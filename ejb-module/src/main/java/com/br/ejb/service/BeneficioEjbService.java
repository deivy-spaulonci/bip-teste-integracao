package com.br.ejb.service;

import com.br.ejb.entity.Beneficio;
import com.br.ejb.exception.BeneficioException;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.xml.ws.ServiceMode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Setter
@Stateless
public class BeneficioEjbService implements BeneficioEjbServiceRemote {

    @PersistenceContext
    private EntityManager entityManager;

    private static final int TENTATIAS = 3;

    public BeneficioEjbService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Beneficio> findAll() {
        TypedQuery<Beneficio> query = entityManager.createQuery("SELECT b FROM Beneficio b", Beneficio.class);
        return query.getResultList();
    }

    @Override
    public Beneficio create(Beneficio beneficio) {
        entityManager.persist(beneficio);
        return beneficio;
    }

    @Override
    public Beneficio findById(Long id) {
        return entityManager.find(Beneficio.class, id);
    }

    @Override
    public void delete(Long id) {
        Beneficio b = Optional.ofNullable(entityManager.find(Beneficio.class, id))
                .orElseThrow(() -> new BeneficioException(BeneficioException.Tipo.NOT_FOUND, "Beneficio não encontrado."));
        entityManager.remove(b);
    }
    @Override
    public Beneficio update(Beneficio beneficio) {
        return entityManager.merge(beneficio);
    }


    @Override
    public void transfer(Long fromid, Long toid, BigDecimal amount) {
        int attempt = 0;
        boolean success = false;

        while (!success) {
            attempt++;
            try {
                doTransfer(fromid, toid, amount);
                success = true;
            } catch (OptimisticLockException e) {
                if (attempt >= TENTATIAS)
                    throw new RuntimeException("erro: tentativas.", e);
            }
        }
    }


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void doTransfer(Long fromId, Long toId, BigDecimal amount) {
        Beneficio from = Optional.ofNullable(entityManager.find(Beneficio.class, fromId))
                .orElseThrow(() -> new BeneficioException(BeneficioException.Tipo.NOT_FOUND, "Origem não encontrada."));

        Beneficio to = Optional.ofNullable(entityManager.find(Beneficio.class, toId))
                .orElseThrow(() -> new BeneficioException(BeneficioException.Tipo.NOT_FOUND, "Destino não encontrado."));

        Optional.ofNullable(amount)
                .filter(a -> a.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new BeneficioException(BeneficioException.Tipo.INVALID_AMOUNT, "Valor deve ser positivo."));

        Optional.ofNullable(amount)
                .filter(a -> a.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new BeneficioException(BeneficioException.Tipo.INVALID_AMOUNT, "Valor deve ser positivo."));

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        entityManager.merge(from);
        entityManager.merge(to);
    }

}