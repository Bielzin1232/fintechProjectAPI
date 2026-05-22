package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.dtos.PixSendRequest;
import com.fintechProject.fintechProject.dtos.PixSendResponse;
import com.fintechProject.fintechProject.entity.ChavePix;
import com.fintechProject.fintechProject.entity.TransacaoPix;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.exceptions.RegraDeNegocioException;
import com.fintechProject.fintechProject.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PixService {

    private final CarteiraRepository carteiraRepository;
    private final TransacaoPixRepository transacaoPixRepository;
    private final ChavePixRepository chavePixRepository;

    public PixService(CarteiraRepository carteiraRepository, TransacaoPixRepository transacaoPixRepository, ChavePixRepository chavePixRepository) {
        this.carteiraRepository = carteiraRepository;
        this.transacaoPixRepository = transacaoPixRepository;
        this.chavePixRepository = chavePixRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public PixSendResponse enviarPix(PixSendRequest data, String chaveIdempotencia, Usuario usuarioLogado) {

        if (transacaoPixRepository.existsByChaveIdempotencia(chaveIdempotencia)) {
            throw new RegraDeNegocioException("Você já realizou esta ação, por favor aguarde!");
        }

        ChavePix chavePix = chavePixRepository.findByValor(data.chaveDestinatario())
                .orElseThrow(() -> new RegraDeNegocioException("A chave PIX informada não foi encontrada!"));

        Usuario usuarioDestinatario = carteiraRepository.findByChavepix(data.chaveDestinatario());

        if (usuarioLogado.getId().equals(usuarioDestinatario.getId())) {
            throw new RegraDeNegocioException("Você não pode realizar um PIX para si mesmo!");
        }

        if (usuarioLogado.getCarteira().getSaldo().compareTo(data.valor()) < 0) {
            throw new RegraDeNegocioException("O seu saldo não é suficiente para esta ação");
        }

        usuarioLogado.getCarteira().setSaldo(usuarioLogado.getCarteira().getSaldo().subtract(data.valor()));
        usuarioDestinatario.getCarteira().setSaldo(usuarioDestinatario.getCarteira().getSaldo().add(data.valor()));

        TransacaoPix transacaoPix = new TransacaoPix();
        transacaoPix.setValor(data.valor());
        transacaoPix.setData(LocalDateTime.now());
        transacaoPix.setRemetente(usuarioLogado.getCarteira());
        transacaoPix.setDestinatario(usuarioDestinatario.getCarteira());
        transacaoPix.setChavePixDestinatario(data.chaveDestinatario());
        transacaoPix.setChaveIdempotencia(chaveIdempotencia);

        transacaoPixRepository.save(transacaoPix);

        return new PixSendResponse(
                usuarioLogado.getNome(),
                usuarioDestinatario.getNome(),
                data.valor()
        );
    }
    
    @Transactional(readOnly = true)
    public Page<PixSendResponse> gerarExtratoPix(Usuario usuarioLogado, Pageable pageable) {

        return transacaoPixRepository.findAll(pageable).map(transacao -> new PixSendResponse(
                transacao.getRemetente().getUsuario().getNome(),
                transacao.getDestinatario().getUsuario().getNome(),
                transacao.getValor()
        ));
    }
    
    
    
    
}
