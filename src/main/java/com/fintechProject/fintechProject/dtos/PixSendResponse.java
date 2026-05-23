package com.fintechProject.fintechProject.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PixSendResponse(
        String remententeNome,
        String destinatarioNome,
        BigDecimal valor,
        LocalDateTime dataCriacao,
        UUID idTransacao




) {
}
