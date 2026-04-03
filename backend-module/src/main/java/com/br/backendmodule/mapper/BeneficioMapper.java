package com.br.backendmodule.mapper;

import com.br.backendmodule.dto.BeneficioDTO;
import com.br.backendmodule.dto.request.BeneficioRequestCreateDTO;
import com.br.backendmodule.dto.request.BeneficioRequestUpdateDTO;
import com.br.backendmodule.dto.response.BeneficioResponseDTO;
import com.br.ejb.entity.Beneficio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BeneficioMapper {
    BeneficioMapper INSTANCE = Mappers.getMapper(BeneficioMapper.class);

    BeneficioDTO toDTO(Beneficio entity);

    Beneficio toEntity(BeneficioDTO dto);
    Beneficio toEntity(BeneficioResponseDTO beneficioResponseDTO);
    Beneficio toEntity(BeneficioRequestCreateDTO beneficioRequestCreateDTO);
    Beneficio toEntity(BeneficioRequestUpdateDTO beneficioRequestUpdateDTO);

    BeneficioResponseDTO toResponseDTO(Beneficio beneficio);

    List<BeneficioResponseDTO> toListResponseDTO(List<Beneficio> beneficioList);
}
