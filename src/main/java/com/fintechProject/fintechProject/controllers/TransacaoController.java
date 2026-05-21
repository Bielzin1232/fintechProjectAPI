package com.fintechProject.fintechProject.controllers;


import com.fintechProject.fintechProject.dtos.TransferenciaRequestDTO;
import com.fintechProject.fintechProject.dtos.TransferenciaResponseDTO;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.services.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/transferir")
    public ResponseEntity<TransferenciaResponseDTO> transferencia(
            @RequestBody @Valid TransferenciaRequestDTO dataRequest,
            @RequestHeader("Idempotency-Key") String chaveIdempotencia,
            @AuthenticationPrincipal Usuario usuarioLogado
    ){
        TransferenciaResponseDTO dto = transacaoService.fazerTransferencia(dataRequest,chaveIdempotencia,usuarioLogado);
        return ResponseEntity.ok().body(dto);
    }


}
