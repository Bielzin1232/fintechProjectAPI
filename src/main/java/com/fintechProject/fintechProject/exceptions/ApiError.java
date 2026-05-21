package com.fintechProject.fintechProject.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<CampoErro> errosValidacao
) {
    public record CampoErro(String campo, String mensagem) {}
}