package com.fintechProject.fintechProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PixListRequest(
        @NotBlank(message = "Você precisa informar o cpf do usuário")
        @CPF(message = "CPF inválido!")
        @Size(min = 11, max = 11, message = "O CPF deve conter 11 dígitos")
        String cpfUsuario


) {
}
