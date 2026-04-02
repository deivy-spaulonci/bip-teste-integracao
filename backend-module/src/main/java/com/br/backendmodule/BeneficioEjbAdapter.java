package com.br.backendmodule;

import com.br.ejb.service.BeneficioEjbServiceRemote;
import org.springframework.stereotype.Component;

@Component
public class BeneficioEjbAdapter {

    private final BeneficioEjbServiceRemote service;

    public BeneficioEjbAdapter(BeneficioEjbServiceRemote service) {
        this.service = service;
    }

    public BeneficioEjbServiceRemote getService() {
        return service;
    }
}
