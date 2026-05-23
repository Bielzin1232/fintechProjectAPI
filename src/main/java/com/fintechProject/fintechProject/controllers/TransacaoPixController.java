package com.fintechProject.fintechProject.controllers;

import com.fintechProject.fintechProject.dtos.*;
import com.fintechProject.fintechProject.entity.Usuario;
import com.fintechProject.fintechProject.services.PixService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/pix")
public class TransacaoPixController {

    private final PixService pixService;

    public TransacaoPixController(PixService pixService) {
        this.pixService = pixService;
    }

    @PostMapping("/excluir")
    public ResponseEntity<PixDeleteResponse> excluirChavePix(
            @RequestBody @Valid PixDeleteRequest data,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        return pixService.excluirChavePix(data, usuarioLogado);
    }

    @PostMapping("/enviar")
    public ResponseEntity<PixSendResponse> enviarPix(
            @RequestBody @Valid PixSendRequest data,
            @RequestHeader("chave-idempotencia") String chaveIdempotencia,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PixSendResponse response = pixService.enviarPix(data, chaveIdempotencia, usuarioLogado);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/criar")
    public ResponseEntity<PixCreationResponse> criarChavePix(
            @RequestBody @Valid PixCreationRequest data,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        PixCreationResponse response = pixService.criarChavePix(data, usuarioLogado);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/extrato")
    public ResponseEntity<Page<PixSendResponse>> gerarExtratoPix(
            @AuthenticationPrincipal Usuario usuarioLogado,
            Pageable pageable
    ) {
        Page<PixSendResponse> response = pixService.gerarExtratoPix(usuarioLogado, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<PixListResponse> listarChavesPix(
            @AuthenticationPrincipal Usuario usuarioLogado) {

        var response = pixService.listarChavesPix(usuarioLogado);
        return ResponseEntity.ok(response);
    }
}
