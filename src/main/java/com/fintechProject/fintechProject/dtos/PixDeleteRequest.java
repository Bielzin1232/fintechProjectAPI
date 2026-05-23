package com.fintechProject.fintechProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PixDeleteRequest(
        @NotBlank(message = "A chave pix precisa ser informada!")
        @Size(min = 11,max = 255, message = "A chave PIX deve conter no mínimo 11 e máximo de 255 caracteres")
        String valorChave

) {
}
