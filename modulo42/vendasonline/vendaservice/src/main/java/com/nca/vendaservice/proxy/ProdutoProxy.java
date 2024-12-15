package com.nca.vendaservice.proxy;

import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.response.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "produto-service")
public interface ProdutoProxy {

    @GetMapping(value = "/produto/filtrar/{campo}/{valor}")
    public ResponseEntity<Collection<Produto>> filtrarProdutoPor(
            @PathVariable(value = "campo", required = true) String campo
            , @PathVariable(value = "valor", required = true) String valor) throws DAOException;
}
