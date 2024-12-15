package com.nca.vendaservice.resources;


import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.services.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/venda")
@Tag(name = "Venda Endpoint")
public class VendaResource {

    private final VendaService service;

    @Autowired
    public VendaResource(VendaService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cadastra um Produto")
    public ResponseEntity<Venda> cadastrar(@RequestBody @Valid Venda venda) throws DAOException {
        return ResponseEntity.ok(service.salvar(venda));
    }
}
