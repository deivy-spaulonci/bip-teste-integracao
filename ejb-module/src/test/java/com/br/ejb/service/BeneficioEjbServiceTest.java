package com.br.ejb.service;

import com.br.ejb.entity.Beneficio;
import com.br.ejb.exception.BeneficioException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BeneficioEjbServiceTest {

    private EntityManager em;
    private BeneficioEjbService service;

    @BeforeEach
    void setup() {
        em = Mockito.mock(EntityManager.class);
        service = new BeneficioEjbService();
        service.setEntityManager(em);
    }

    private BeneficioEjbService createServiceWithMocks(Beneficio from, Beneficio to, EntityManager em)
            throws Exception {
        when(em.find(Beneficio.class, 1L)).thenReturn(from);
        when(em.find(Beneficio.class, 2L)).thenReturn(to);
        when(em.merge(any(Beneficio.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BeneficioEjbService realService = new BeneficioEjbService();
        Field field = BeneficioEjbService.class.getDeclaredField("entityManager");
        field.setAccessible(true);
        field.set(realService, em);
        return realService;
    }

    @Test
    void testCreateBeneficio() {
        Beneficio beneficio = Beneficio.builder()
                .id(null)
                .nome("Vela Gas")
                .valor(new BigDecimal("100.00"))
                .build();
                service.create(beneficio);
        verify(em, times(1)).persist(beneficio);
    }

    @Test
    void testFindById() {
        Beneficio beneficio = Beneficio.builder()
                .id(null)
                .nome("Vale Gas")
                .valor(new BigDecimal("130.00"))
                .build();
        service.create(beneficio);

        when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);

        Beneficio result = service.findById(1L);

        assertNotNull(result);
        assertEquals("Vale Gas", result.getNome());
    }

    // READ - findAll
    @Test
    void testFindAll() {
        Beneficio beneficioa = Beneficio.builder()
                .id(1L)
                .nome("Vale Gas")
                .valor(new BigDecimal("130.00"))
                .build();

        Beneficio beneficiob = Beneficio.builder()
                .id(2L)
                .nome("Vela Gases")
                .valor(new BigDecimal("30.00"))
                .build();

        TypedQuery<Beneficio> query = Mockito.mock(TypedQuery.class);
        when(em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(beneficioa, beneficiob));

        List<Beneficio> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("Vale Gas", result.get(0).getNome());
    }

    @Test
    void testUpdateBeneficio() {
        Beneficio beneficio = Beneficio.builder()
                .id(1L)
                .nome("Vela Gas")
                .valor(new BigDecimal("130.00"))
                .build();
        when(em.merge(beneficio)).thenReturn(beneficio);

        Beneficio updated = service.update(beneficio);

        assertNotNull(updated);
        verify(em, times(1)).merge(beneficio);
    }

    @Test
    void testTransferSuccess() {
        Beneficio bfrom = Beneficio.builder().id(1L).nome("From").valor(new BigDecimal("2000.00")).build();
        Beneficio bto = Beneficio.builder().id(1L).nome("To").valor(new BigDecimal("100.00")).build();

        when(em.find(Beneficio.class, 1L)).thenReturn(bfrom);
        when(em.find(Beneficio.class, 2L)).thenReturn(bto);

        service.transfer(1L, 2L, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("1800.00"), bfrom.getValor());
        assertEquals(new BigDecimal("300.00"), bto.getValor());

        verify(em, times(1)).merge(bfrom);
        verify(em, times(1)).merge(bto);
    }

    @Test
    void testDeleteBeneficio() {
        Beneficio beneficio = Beneficio.builder()
                .id(1L)
                .nome("Vela Gas")
                .valor(new BigDecimal("130.00"))
                .build();
        when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);

        service.delete(1L);

        verify(em, times(1)).remove(beneficio);
    }

    @Test
    void testDeleteBeneficioNotFound() {
        when(em.find(Beneficio.class, 99L)).thenReturn(null);
        assertThrows(BeneficioException.class, () -> service.delete(99L));
    }


    @Test
    void testTransferInvalidAmount() {
        Beneficio bfrom = Beneficio.builder().id(1L).nome("From").valor(new BigDecimal("100.00")).build();
        Beneficio bto = Beneficio.builder().id(1L).nome("To").valor(new BigDecimal("50.00")).build();


        when(em.find(Beneficio.class, 1L)).thenReturn(bfrom);
        when(em.find(Beneficio.class, 2L)).thenReturn(bto);

        BeneficioException exception = assertThrows(BeneficioException.class,
                () -> service.transfer(1L, 2L, new BigDecimal("-10.00")));
        assertEquals(BeneficioException.Tipo.INVALID_AMOUNT, exception.getTipo());
    }


    @Test
    void testMultiThreadConcurrentTransfers() throws Exception {
        EntityManager em = Mockito.mock(EntityManager.class);

        Beneficio from = new Beneficio();
        from.setId(1L);
        from.setValor(new BigDecimal("1000"));

        Beneficio to = new Beneficio();
        to.setId(2L);
        to.setValor(new BigDecimal("1000"));

        BeneficioEjbService realService = createServiceWithMocks(from, to, em);
        BeneficioEjbService spyService = Mockito.spy(realService);

        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal amount = new BigDecimal("100");

        doThrow(new OptimisticLockException("teste de conflito simulado"))
                .doAnswer(invocation -> {
                    from.setValor(from.getValor().subtract(amount));
                    to.setValor(to.getValor().add(amount));
                    return null;
                })
                .when(spyService).doTransfer(fromId, toId, amount);

        int threads = 2;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    spyService.transfer(fromId, toId, amount);
                } catch (Exception e) {
                    System.out.println("Thread falhou: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        assertEquals(new BigDecimal("800"), from.getValor(), "Saldo origem de  800");
        assertEquals(new BigDecimal("1200"), to.getValor(), "Saldo destino de  1200");

        verify(spyService, atLeast(3)).doTransfer(fromId, toId, amount);
    }

}
