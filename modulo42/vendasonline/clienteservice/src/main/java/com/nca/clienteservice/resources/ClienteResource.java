package com.nca.clienteservice.resources;

import com.nca.clienteservice.domain.model.Cliente;
import com.nca.clienteservice.exceptions.DAOException;
import com.nca.clienteservice.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/cliente")
@Tag(name = "Cliente Endpoint")
public class ClienteResource {

    private final ClienteService clienteService;

    @Autowired
    public ClienteResource(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os registros ativos")
    public ResponseEntity<Page<Cliente>> listar(Pageable pageable) throws DAOException {
        return ResponseEntity.ok(clienteService.listar(pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um cliente por id")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable(value = "id", required = true) String id)
            throws DAOException {
        return ResponseEntity.ok(clienteService.buscar(id));
    }

    @GetMapping(value = "isCadastrado/{id}")
    @Operation(summary = "Verifica se o Cliente está cadastrado")
    public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id", required = true) Long id)
            throws DAOException {
        return ResponseEntity.ok(clienteService.isCadastrado(id));
    }

    @GetMapping(value = "/{campo}/{valor}")
    @Operation(summary = "Busca um cliente por parâmetro e valor")
    public ResponseEntity<Collection<Cliente>> filtrarClientesPor(
            @PathVariable(value = "campo", required = true) String campo
            , @PathVariable(value = "valor", required = true) String valor) throws DAOException {
        Collection<Cliente> list = clienteService.filtrarProdutosPor(campo, valor);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Cadastra um Cliente")
    public ResponseEntity<Cliente> cadastrar(@RequestBody @Valid Cliente cliente) throws DAOException {
        return ResponseEntity.ok(clienteService.cadastrar(cliente));
    }

    @GetMapping(value = "/cpf/{cpf}")
    @Operation(summary = "Busca um Cliente por cpf")
    public ResponseEntity<Cliente> buscarPorCPF(@PathVariable(value = "cpf", required = true) String cpf) {
        return ResponseEntity.ok(clienteService.buscarPorCpf(cpf));
    }

    @PutMapping
    @Operation(summary = "Atualiza um Cliente")
    public ResponseEntity<Cliente> atualizar(@RequestBody @Valid Cliente cliente) {
        return ResponseEntity.ok(clienteService.atualizar(cliente));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remove um Cliente pelo seu id")
    public ResponseEntity<Void> remover(@PathVariable(value = "id", required = true) String id) {
        clienteService.remover(id);
        return ResponseEntity.ok().build();
    }
}
