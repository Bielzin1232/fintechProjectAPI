package com.fintechProject.fintechProject.controllers;

import com.fintechProject.fintechProject.dtos.LoginRequest;
import com.fintechProject.fintechProject.dtos.RegistrarUsuarioDTO;
import com.fintechProject.fintechProject.dtos.UsuarioResponseDTO;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.services.TokenService;
import com.fintechProject.fintechProject.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }


    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody @Valid RegistrarUsuarioDTO data){
        UsuarioResponseDTO dto = usuarioService.registrarUsuario(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest data){

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.cpf(), data.senha());

        var auth = authenticationManager.authenticate(usernamePassword);

        String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }
}