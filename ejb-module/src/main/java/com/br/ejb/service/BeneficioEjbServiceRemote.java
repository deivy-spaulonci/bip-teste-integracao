package com.br.ejb.service;

import com.br.ejb.entity.Beneficio;
import jakarta.ejb.Remote;

import java.math.BigDecimal;
import java.util.List;

@Remote
public interface BeneficioEjbServiceRemote {
    List<Beneficio> findAll();
    Beneficio create(Beneficio beneficio);
    Beneficio findById(Long id);
    Beneficio update(Beneficio beneficio);
    void delete(Long id);

    void transfer(Long fromId, Long toId, BigDecimal amount);
    void doTransfer(Long fromId, Long toId, BigDecimal amount);

}
