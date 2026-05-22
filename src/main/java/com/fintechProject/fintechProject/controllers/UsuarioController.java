package com.fintechProject.fintechProject.controllers;

import com.fintechProject.fintechProject.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {


    @GetMapping("/meu-saldo")
    public ResponseEntity<Map<String,BigDecimal>> consultarSaldo(@AuthenticationPrincipal
    Usuario usuarioLogado) {
        BigDecimal saldo = usuarioLogado.getCarteira().getSaldo();
        return ResponseEntity.ok(Map.of("saldo",saldo));
    }


}
