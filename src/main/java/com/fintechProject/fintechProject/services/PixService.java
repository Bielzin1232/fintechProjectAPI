package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.dtos.*;
import com.fintechProject.fintechProject.entity.Carteira;
import com.fintechProject.fintechProject.entity.ChavePix;
import com.fintechProject.fintechProject.entity.TransacaoPix;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.exceptions.RegraDeNegocioException;
import com.fintechProject.fintechProject.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PixService {

    private final CarteiraRepository carteiraRepository;
    private final TransacaoPixRepository transacaoPixRepository;
    private final ChavePixRepository chavePixRepository;
    private final UsuarioRepository usuarioRepository;

    public PixService(CarteiraRepository carteiraRepository,
                      TransacaoPixRepository transacaoPixRepository,
                      ChavePixRepository chavePixRepository,
                      UsuarioRepository usuarioRepository) {
        this.carteiraRepository = carteiraRepository;
        this.transacaoPixRepository = transacaoPixRepository;
        this.chavePixRepository = chavePixRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public PixSendResponse enviarPix(PixSendRequest data, String chaveIdempotencia, Usuario usuarioLogado) {

        if (transacaoPixRepository.existsByChaveIdempotencia(chaveIdempotencia)) {
            throw new RegraDeNegocioException("Você já realizou esta ação, por favor aguarde!");
        }

        ChavePix chavePixDestino = chavePixRepository.findByValor(data.chaveDestinatario())
                .orElseThrow(() -> new RegraDeNegocioException("A chave PIX informada não foi encontrada!"));
        Usuario usuarioDestinatario = chavePixDestino.getUsuario();

        Usuario remetente = usuarioRepository.findById(usuarioLogado.getId())
                .orElseThrow(() -> new RegraDeNegocioException("Usuário remetente não encontrado!"));

        if (remetente.getId().equals(usuarioDestinatario.getId())) {
            throw new RegraDeNegocioException("Você não pode realizar um PIX para si mesmo!");
        }

        int qtdChavesRemetente = chavePixRepository.countByUsuarioId(remetente.getId());
        if (qtdChavesRemetente == 0) {
            throw new RegraDeNegocioException("Você precisa ter pelo menos uma chave PIX cadastrada para enviar um PIX!");
        }

        Carteira carteiraRemetente = remetente.getCarteira();
        Carteira carteiraDestinatario = usuarioDestinatario.getCarteira();

        if (carteiraRemetente.getSaldo().compareTo(data.valor()) < 0) {
            throw new RegraDeNegocioException("O seu saldo não é suficiente para esta ação");
        }

        carteiraRemetente.setSaldo(carteiraRemetente.getSaldo().subtract(data.valor()));
        carteiraDestinatario.setSaldo(carteiraDestinatario.getSaldo().add(data.valor()));

        TransacaoPix transacaoPix = new TransacaoPix();
        transacaoPix.setValor(data.valor());
        transacaoPix.setData(LocalDateTime.now());
        transacaoPix.setRemetente(carteiraRemetente);
        transacaoPix.setDestinatario(carteiraDestinatario);
        transacaoPix.setChavePixDestinatario(data.chaveDestinatario());
        transacaoPix.setChaveIdempotencia(chaveIdempotencia);

        transacaoPixRepository.save(transacaoPix);

        return new PixSendResponse(
                remetente.getNome(),
                usuarioDestinatario.getNome(),
                transacaoPix.getValor(),
                transacaoPix.getData(),
                transacaoPix.getId()
        );
    }

    @Transactional(readOnly = true)
    public Page<PixSendResponse> gerarExtratoPix(Usuario usuarioLogado, Pageable pageable) {
        return transacaoPixRepository.findByRemetenteUsuarioIdOrDestinatarioUsuarioId(usuarioLogado.getId(), usuarioLogado.getId(), pageable)
                .map(transacao -> new PixSendResponse(
                        transacao.getRemetente().getUsuario().getNome(),
                        transacao.getDestinatario().getUsuario().getNome(),
                        transacao.getValor(),
                        transacao.getData(),
                        transacao.getId()
                ));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<PixDeleteResponse> excluirChavePix(PixDeleteRequest data, Usuario usuarioLogado) {
        ChavePix chavePix = chavePixRepository.findByValor(data.valorChave())
                .orElseThrow(() -> new RegraDeNegocioException("A chave PIX informada não foi encontrada!"));

        if (!chavePix.getUsuario().getId().equals(usuarioLogado.getId())) {
            throw new RegraDeNegocioException("Você não tem permissão para excluir esta chave PIX!");
        }

        chavePixRepository.delete(chavePix);

        return ResponseEntity.ok(new PixDeleteResponse(
                chavePix.getValor(),
                LocalDateTime.now()
        ));
    }

    @Transactional(readOnly = true)
    public PixListResponse listarChavesPix(PixListRequest data, Usuario usuarioLogado) {
        if (!usuarioLogado.getCpf().equals(data.cpfUsuario())) {
            throw new RegraDeNegocioException("Você não tem permissão para listar chaves de outro usuário!");
        }

        UserDetails userDetails = usuarioRepository.findByCpf(data.cpfUsuario());

        if (userDetails == null) {
            throw new RegraDeNegocioException("Usuário não encontrado para o CPF informado.");
        }
        Usuario usuario = (Usuario) userDetails;
        return new PixListResponse(usuario.getChavesPix());
    }

    @Transactional(rollbackFor = Exception.class)
    public PixCreationResponse criarChavePix(PixCreationRequest data, Usuario usuarioLogado) {

        int quantidadeDeChaves = chavePixRepository.countByUsuarioId(usuarioLogado.getId());

        if (quantidadeDeChaves >= 5) {
            throw new RegraDeNegocioException("Você atingiu o limite máximo de 5 chaves PIX!");
        }

        if (chavePixRepository.findByValor(data.valorChave()).isPresent()) {
            throw new RegraDeNegocioException("Esta chave PIX já está cadastrada!");
        }

        ChavePix chavePix = new ChavePix();
        chavePix.setTipo(data.tipoPix());
        chavePix.setValor(data.valorChave());
        chavePix.setUsuario(usuarioLogado);

        ChavePix chavePixSalva = chavePixRepository.save(chavePix);

        return new PixCreationResponse(
                chavePixSalva.getTipo(),
                chavePixSalva.getValor()
        );
    }
}