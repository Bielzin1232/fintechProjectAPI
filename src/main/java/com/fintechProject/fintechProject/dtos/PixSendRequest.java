package com.fintechProject.fintechProject.dtos;

import java.math.BigDecimal;

public record PixSendRequest(
        String chaveDestinatario,
        BigDecimal valor



) {
}
