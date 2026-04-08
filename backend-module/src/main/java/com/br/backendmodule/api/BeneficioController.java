package com.br.backendmodule.api;

import com.br.backendmodule.dto.TransferRequestDTO;
import com.br.backendmodule.dto.request.BeneficioRequestCreateDTO;
import com.br.backendmodule.dto.request.BeneficioRequestUpdateDTO;
import com.br.backendmodule.dto.response.BeneficioResponseDTO;
import com.br.backendmodule.service.BeneficioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/beneficios")
@Tag(name = "Benefícios", description = "apis para gerenciamento de benefícios")
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService)
    {
        this.beneficioService = beneficioService;
    }

    @GetMapping
    @Operation(summary = "Listar os benefícios")
    @ResponseStatus(HttpStatus.OK)
    public List<BeneficioResponseDTO> list()
    {
        return beneficioService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BeneficioResponseDTO> getById(@PathVariable("id") Long id) {
        return ok(beneficioService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Benefício que ja existe")
    public ResponseEntity<BeneficioResponseDTO> update(@PathVariable("id") Long id, @Valid @RequestBody BeneficioRequestUpdateDTO beneficioRequestUpdateDTO) {
        return ok(beneficioService.update(id,beneficioRequestUpdateDTO));
    }

    @PostMapping
    @Operation(summary = "Novo benefício")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BeneficioResponseDTO> create(@Valid @RequestBody BeneficioRequestCreateDTO beneficioRequestCreateDTO) {
        return ok(beneficioService.create(beneficioRequestCreateDTO));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transferir saldo entre benefícios")
    @ResponseStatus(HttpStatus.OK)
    public void transfer(@Valid @RequestBody TransferRequestDTO request) {
        beneficioService.transfer(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Excluir um benefício")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try{
            beneficioService.delet(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
