package com.fintechProject.fintechProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PixSendRequest(
        @NotBlank(message = "A chave do destinatário é obrigatória")
        @Size(max = 255,message = "A chave não pode exceder 255 caracteres!")
        String chaveDestinatario,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor
) {
}
