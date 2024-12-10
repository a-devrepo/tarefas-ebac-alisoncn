package com.nca.produtoservice.resources;

import com.nca.produtoservice.domain.model.Produto;
import com.nca.produtoservice.exceptions.DAOException;
import com.nca.produtoservice.services.ProdutoService;
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
@RequestMapping(value = "/produto")
@Tag(name = "Produto Endpoint")
public class ProdutoResource {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoResource(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os registros ativos")
    public ResponseEntity<Page<Produto>> listar(Pageable pageable) throws DAOException {
        return ResponseEntity.ok(produtoService.listar(pageable));
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um produto por id")
    public ResponseEntity<Produto> buscarPorId(@PathVariable(value = "id", required = true) String id)
            throws DAOException {
        return ResponseEntity.ok(produtoService.buscar(id));
    }

    @GetMapping(value = "filtrar/{campo}/{valor}")
    @Operation(summary = "Busca um produto por parâmetro e valor")
    public ResponseEntity<Collection<Produto>> filtrarProdutosPor(
            @PathVariable(value = "campo", required = true) String campo
            , @PathVariable(value = "valor", required = true) String valor) throws DAOException {
        Collection<Produto> list = produtoService.filtrarProdutosPor(campo, valor);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Operation(summary = "Cadastra um Produto")
    public ResponseEntity<Produto> cadastrar(@RequestBody @Valid Produto produto) throws DAOException {
        return ResponseEntity.ok(produtoService.cadastrar(produto));
    }

    @PutMapping
    @Operation(summary = "Atualiza um Produto")
    public ResponseEntity<Produto> atualizar(@RequestBody @Valid Produto produto) {
        return ResponseEntity.ok(produtoService.atualizar(produto));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remove um Produto pelo seu id")
    public ResponseEntity<Void> remover(@PathVariable(value = "id", required = true) String id) {
        produtoService.remover(id);
        return ResponseEntity.ok().build();
    }
}
