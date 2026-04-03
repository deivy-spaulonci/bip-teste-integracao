package com.br.backendmodule.dto.response;

import java.math.BigDecimal;

public record BeneficioResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        Boolean ativo,
        Long version
) {}
