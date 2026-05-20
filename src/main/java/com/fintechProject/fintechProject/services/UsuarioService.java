package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.dtos.RegistrarUsuarioDTO;
import com.fintechProject.fintechProject.dtos.UsuarioResponseDTO;
import com.fintechProject.fintechProject.entity.Carteira;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.enums.UserRole;
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
        Usuario usuario = new Usuario();
        usuario.setNome(data.nome());
        usuario.setCpf(data.cpf());
        usuario.setEmail(data.email());
        usuario.setNumeroTelefone(data.numeroTelefone());


        usuario.setSenha(passwordEncoder.encode(data.senha()));
        usuario.setRole(UserRole.USER);
        Carteira carteira = new Carteira();
        carteira.setUsuario(usuario);
        usuario.setCarteira(carteira);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return new UsuarioResponseDTO(usuarioSalvo.getId(),usuarioSalvo.getNome(),usuarioSalvo.getEmail());
    }



}
