package com.br.backendmodule.api;

import com.br.backendmodule.BeneficioEjbAdapter;
import com.br.backendmodule.dto.BeneficioDTO;
import com.br.backendmodule.dto.TransferRequestDTO;
import com.br.backendmodule.mapper.BeneficioMapper;
import com.br.ejb.entity.Beneficio;
import com.br.ejb.service.BeneficioEjbServiceRemote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BeneficioEjbServiceRemote beneficioEjbService;

    @MockBean
    private BeneficioMapper beneficioMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public BeneficioEjbAdapter beneficioEjbAdapter(BeneficioEjbServiceRemote service) {
            return new BeneficioEjbAdapter(service);
        }
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testList() throws Exception {
        Beneficio b1 = new Beneficio();
        b1.setId(1L);
        b1.setNome("Vale Gas");

        Beneficio b2 = new Beneficio();
        b2.setId(2L);
        b2.setNome("Vale Refeição");

        BeneficioDTO d1 = new BeneficioDTO();
        d1.setId(1L);
        d1.setNome("Vale Gas");

        BeneficioDTO d2 = new BeneficioDTO();
        d2.setId(2L);
        d2.setNome("Vale Refeição");

        when(beneficioEjbService.findAll()).thenReturn(Arrays.asList(b1, b2));
        when(beneficioMapper.toDTO(b1)).thenReturn(d1);
        when(beneficioMapper.toDTO(b2)).thenReturn(d2);

        mockMvc.perform(get("/api/v1/beneficios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Vale Gas"))
                .andExpect(jsonPath("$[1].nome").value("Vale Refeição"));
    }

    @Test
    void testGetById() throws Exception {
        Beneficio b = new Beneficio();
        b.setId(1L);
        b.setNome("Vale Refeição");

        BeneficioDTO dto = new BeneficioDTO();
        dto.setId(1L);
        dto.setNome("Vale Refeição");

        when(beneficioEjbService.findById(1L)).thenReturn(b);
        when(beneficioMapper.toDTO(b)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/beneficios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Vale Refeição"));
    }

    @Test
    void testCreate() throws Exception {
        BeneficioDTO dto = new BeneficioDTO();
        dto.setNome("Novo Benefício");
        dto.setValor(new BigDecimal("110.00"));

        Beneficio entity = new Beneficio();
        entity.setNome("Novo Benefício");
        entity.setValor(new BigDecimal("110.00"));

        Beneficio created = new Beneficio();
        created.setId(1L);
        created.setNome("Novo Benefício");
        created.setValor(new BigDecimal("110.00"));

        BeneficioDTO createdDto = new BeneficioDTO();
        createdDto.setId(1L);
        createdDto.setNome("Novo Benefício");

        when(beneficioMapper.toEntity(any(BeneficioDTO.class))).thenReturn(entity);
        when(beneficioEjbService.create(any(Beneficio.class))).thenReturn(created);
        when(beneficioMapper.toDTO(created)).thenReturn(createdDto);

        mockMvc.perform(post("/api/v1/beneficios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Novo Benefício"));
    }

    @Test
    void testUpdate() throws Exception {
        BeneficioDTO dto = new BeneficioDTO();
        dto.setNome("Nome Atualizado");
        dto.setValor(new BigDecimal("45.00"));

        Beneficio entity = new Beneficio();
        entity.setId(1L);
        entity.setNome("Nome Atualizado");
        entity.setValor(new BigDecimal("45.00"));

        Beneficio updated = new Beneficio();
        updated.setId(1L);
        updated.setNome("Nome Atualizado");
        updated.setValor(new BigDecimal("45.00"));

        BeneficioDTO updatedDto = new BeneficioDTO();
        updatedDto.setId(1L);
        updatedDto.setNome("Nome Atualizado");

        when(beneficioMapper.toEntity(any(BeneficioDTO.class))).thenReturn(entity);
        when(beneficioEjbService.update(any(Beneficio.class))).thenReturn(updated);
        when(beneficioMapper.toDTO(updated)).thenReturn(updatedDto);

        mockMvc.perform(put("/api/v1/beneficios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/beneficios/1"))
                .andExpect(status().isNoContent());

        verify(beneficioEjbService, times(1)).delete(1L);
    }

    @Test
    void testTransfer() throws Exception {
        TransferRequestDTO request = new TransferRequestDTO(1L,2L,new BigDecimal("55.00"));
        mockMvc.perform(post("/api/v1/beneficios/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(beneficioEjbService, times(1)).transfer(eq(1L), eq(2L), any(BigDecimal.class));
    }
}