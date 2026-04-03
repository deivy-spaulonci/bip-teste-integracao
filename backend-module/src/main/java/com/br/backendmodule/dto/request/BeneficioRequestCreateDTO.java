package com.br.backendmodule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BeneficioRequestCreateDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Valor é obrigatório")
        String descricao,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal valor,

        Boolean ativo,
        Long version
) {
}
