package com.fintechProject.fintechProject.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(

        @NotBlank(message = "{cpf.em.branco}")
        @CPF(message = "{cpf.invalido}")
        String cpfDestinatario,

        @NotNull(message = "{valor.em.branco}")
        @DecimalMin(value = "0.01", message = "O valor mínimo para transferência é R$ 0,01")
        BigDecimal valor,

        @NotBlank(message = "{palavra.passe.obrigatoria}")
        String palavraPasse



) {
}
