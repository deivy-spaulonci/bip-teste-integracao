package com.br.backendmodule.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDTO(
        @NotNull(message = "ID de origem é obrigatório")
        Long fromId,
        @NotNull(message = "ID de destino é obrigatório")
        Long toId,
        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal amount)
{}
