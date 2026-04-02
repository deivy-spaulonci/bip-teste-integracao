package com.br.backendmodule.mapper;

import com.br.backendmodule.dto.BeneficioDTO;
import com.br.ejb.entity.Beneficio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeneficioMapper {
    BeneficioMapper INSTANCE = Mappers.getMapper(BeneficioMapper.class);

    BeneficioDTO toDTO(Beneficio entity);

    Beneficio toEntity(BeneficioDTO dto);
}
