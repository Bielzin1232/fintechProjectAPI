package com.fintechProject.fintechProject.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponseDTO(

        UUID idTransacao,
        BigDecimal valor,
        LocalDateTime data,
        String nomeRemetente,
        String nomeDestinatario

) {
}
