package com.fintechProject.fintechProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record LoginRequest(

        @CPF(message = "{cpf.invalido}")
        @NotBlank(message = "{cpf.em.branco}")
        @Size(min = 11,message = "{cpf.tamanho.invalido}")
        String cpf,
        @NotBlank(message = "{senha.em.branco}")
        @Size(min = 8,max = 45,message = "{senha.tamanho.limite}")
        String senha



) {
}
