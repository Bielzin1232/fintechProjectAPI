package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.dtos.TransferenciaRequestDTO;
import com.fintechProject.fintechProject.dtos.TransferenciaResponseDTO;
import com.fintechProject.fintechProject.entity.Transacao;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.exceptions.RegraDeNegocioException;
import com.fintechProject.fintechProject.repository.TransacaoRepository;
import com.fintechProject.fintechProject.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public TransferenciaResponseDTO fazerTransferencia(TransferenciaRequestDTO data,String chaveIdempotencia, Usuario usuarioLogado) {

        Usuario usuarioDestinatario = (Usuario) usuarioRepository.findByCpf(data.cpfDestinatario());

        if (transacaoRepository.existsByChaveIdempotencia(chaveIdempotencia)) {
            throw new RegraDeNegocioException("Você já realizou esta ação, por favor aguarde!");
        }

        if(!passwordEncoder.matches(data.palavraPasse(),usuarioLogado.getPalavraPasse())) {
            throw new RegraDeNegocioException("A palavra passe utilizada é inválida!");
        }
        if(usuarioDestinatario == null) {
            throw new RegraDeNegocioException("O destinatário não foi encontrado em nosso sistema!");
        }

        if(usuarioLogado.getId().equals(usuarioDestinatario.getId())) {
            throw new RegraDeNegocioException("Você não pode transferir para si mesmo!");
        }

        if(usuarioLogado.getCarteira().getSaldo().compareTo(data.valor()) < 0) {
            throw new RegraDeNegocioException("O seu saldo não é suficiente para esta ação");
        }
        usuarioLogado.getCarteira().setSaldo(usuarioLogado.getCarteira().getSaldo().subtract(data.valor()));
        usuarioDestinatario.getCarteira().setSaldo(usuarioDestinatario.getCarteira().getSaldo().add(data.valor()));

        Transacao transacao = new Transacao();
        transacao.setValor(data.valor());
        transacao.setData(LocalDateTime.now());
        transacao.setRemetente(usuarioLogado.getCarteira());
        transacao.setDestinatario(usuarioDestinatario.getCarteira());
        transacao.setChaveIdempotencia(chaveIdempotencia);
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        return new TransferenciaResponseDTO(
                transacaoSalva.getTransacaoID(),
                transacaoSalva.getValor(),
                transacaoSalva.getData(),
                usuarioLogado.getNome(),
                usuarioDestinatario.getNome()
        );
    }



}
