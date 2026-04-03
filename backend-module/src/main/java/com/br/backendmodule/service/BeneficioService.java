package com.br.backendmodule.service;

import com.br.backendmodule.BeneficioEjbAdapter;
import com.br.backendmodule.dto.TransferRequestDTO;
import com.br.backendmodule.dto.request.BeneficioRequestCreateDTO;
import com.br.backendmodule.dto.request.BeneficioRequestUpdateDTO;
import com.br.backendmodule.dto.response.BeneficioResponseDTO;
import com.br.backendmodule.mapper.BeneficioMapper;
import com.br.ejb.entity.Beneficio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeneficioService {
    private static final BeneficioMapper beneficioMapper = BeneficioMapper.INSTANCE;
    private final BeneficioEjbAdapter beneficioEjbAdapter;

    public BeneficioService(BeneficioEjbAdapter beneficioEjbAdapter)
    {
        this.beneficioEjbAdapter = beneficioEjbAdapter;
    }

    public List<BeneficioResponseDTO> findAll()
    {
        return beneficioEjbAdapter.getService().findAll().stream().map(beneficioMapper::toResponseDTO).collect(Collectors.toList());
    }

    public BeneficioResponseDTO findById(Long id)
    {
        return beneficioMapper.toResponseDTO(beneficioEjbAdapter.getService().findById(id));
    }

    public BeneficioResponseDTO update(Long id, BeneficioRequestUpdateDTO beneficioRequestUpdateDTO)
    {
        Beneficio beneficio = beneficioMapper.toEntity(beneficioRequestUpdateDTO);
        beneficio.setId(id);
        if(beneficio.getVersion()==null)
            beneficio.setVersion(0L);
        Beneficio updated = beneficioEjbAdapter.getService().update(beneficio);
        return beneficioMapper.toResponseDTO(updated);
    }

    public BeneficioResponseDTO create(BeneficioRequestCreateDTO beneficioRequestCreateDTO)
    {
        Beneficio beneficio = beneficioMapper.toEntity(beneficioRequestCreateDTO);
        Beneficio created = beneficioEjbAdapter.getService().create(beneficio);
        return beneficioMapper.toResponseDTO(created);

    }

    public void delet(Long id)
    {
        beneficioEjbAdapter.getService().delete(id);
    }

    public void transfer(TransferRequestDTO transferRequestDTO)
    {
        beneficioEjbAdapter.getService().transfer(
                transferRequestDTO.fromId(),
                transferRequestDTO.toId(),
                transferRequestDTO.amount());
    }

}
