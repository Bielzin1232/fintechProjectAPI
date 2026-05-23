package com.fintechProject.fintechProject.dtos;

import com.fintechProject.fintechProject.enums.TipoPix;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PixCreationRequest(
        @NotNull(message = "O tipo da chave PIX é obrigatório")
        TipoPix tipoPix,

        @NotBlank(message = "O valor da chave PIX é obrigatório")
        String valorChave
) {
}
