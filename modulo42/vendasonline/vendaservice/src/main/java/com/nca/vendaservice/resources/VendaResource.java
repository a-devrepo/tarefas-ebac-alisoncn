package com.nca.vendaservice.resources;


import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.dto.VendaDTO;
import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.services.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/venda")
@Tag(name = "Venda Endpoint")
public class VendaResource {

    private final VendaService service;

    @Autowired
    public VendaResource(VendaService service) {
        this.service = service;
    }


    @GetMapping
    @Operation(summary = "Lista todos as Vendas")
    public ResponseEntity<Page<Venda>> listar(Pageable pageable) throws DAOException {
        return ResponseEntity.ok(service.listar(pageable));
    }


    @PostMapping
    @Operation(summary = "Cadastra uma Venda")
    public ResponseEntity<Venda> cadastrar(@RequestBody @Valid Venda venda) throws DAOException {
        return ResponseEntity.ok(service.salvar(venda));
    }
}
