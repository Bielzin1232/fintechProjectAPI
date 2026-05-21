package com.fintechProject.fintechProject.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(

        @NotBlank(message = "O cpf do destinatário precisa ser informado!")
        @CPF(message = "O cpf do destinatário é inválido")
        String cpfDestinatario,

        @NotNull(message = "O valor precisa ser informado!")
        @DecimalMin(value = "0.01", message = "O valor mínimo para transferência é R$ 0,01")
        BigDecimal valor,

        @NotBlank(message = "A palavra passe é obrigatória para a transferência!")
        String palavraPasse



) {
}
