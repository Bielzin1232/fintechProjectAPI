package com.fintechProject.fintechProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record LoginRequest(

        @CPF(message = "O cpf informado é inválido")
        @NotBlank(message = "O cpf não pode ser em branco!")
        @Size(min = 11,message = "O tamanho informado do cpf não é válido!")
        String cpf,
        @NotBlank(message = "A senha não pode ser em branco!")
        @Size(min = 8,max = 45,message = "A senha precisa estar entre 8 e 45 caracteres")
        String senha



) {
}
