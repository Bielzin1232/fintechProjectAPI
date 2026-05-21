package com.fintechProject.fintechProject.services;

import com.fintechProject.fintechProject.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UsuarioRepository repository;

    public AuthenticationService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {

        UserDetails usuario = repository.findByCpf(cpf);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado para o CPF informado!");
        }

        return usuario;
    }
}
