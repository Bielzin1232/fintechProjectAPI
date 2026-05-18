package com.fintechProject.fintechProject.controllers;

import com.fintechProject.fintechProject.dtos.RegistrarUsuarioDTO;
import com.fintechProject.fintechProject.dtos.UsuarioResponseDTO;
import com.fintechProject.fintechProject.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {


    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody @Valid RegistrarUsuarioDTO data){
        UsuarioResponseDTO dto = usuarioService.registrarUsuario(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);



    }


}
