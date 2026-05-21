package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.dtos.RegistrarUsuarioDTO;
import com.fintechProject.fintechProject.dtos.UsuarioResponseDTO;
import com.fintechProject.fintechProject.entity.Carteira;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.enums.UserRole;
import com.fintechProject.fintechProject.exceptions.RegraDeNegocioException;
import com.fintechProject.fintechProject.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;
    public UsuarioService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponseDTO registrarUsuario(RegistrarUsuarioDTO data) {
        if (usuarioRepository.existsByCpf(data.cpf())) {
            throw new RegraDeNegocioException("Este CPF já está cadastrado!");
        }

        if (usuarioRepository.existsByEmail(data.email())) {
            throw new RegraDeNegocioException("Este e-mail já está em uso por outra conta.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(data.nome());
        usuario.setCpf(data.cpf());
        usuario.setEmail(data.email());
        usuario.setNumeroTelefone(data.numeroTelefone());
        usuario.setPalavraPasse(passwordEncoder.encode(data.palavraPasse()));


        usuario.setSenha(passwordEncoder.encode(data.senha()));
        usuario.setRole(UserRole.USER);
        Carteira carteira = new Carteira();
        carteira.setUsuario(usuario);
        usuario.setCarteira(carteira);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioSalvo.getId(),usuarioSalvo.getNome(),usuarioSalvo.getEmail());
    }



}
