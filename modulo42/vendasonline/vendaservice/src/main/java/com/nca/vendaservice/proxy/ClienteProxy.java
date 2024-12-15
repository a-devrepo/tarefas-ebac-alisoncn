package com.nca.vendaservice.proxy;

import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.response.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "cliente-service")
public interface ClienteProxy {

    @GetMapping(value = "/cliente/filtrar/{campo}/{valor}")
    @Operation(summary = "Busca um cliente por parâmetro e valor")
    public ResponseEntity<Collection<Cliente>> filtrarClientesPor(
            @PathVariable(value = "campo", required = true) String campo
            , @PathVariable(value = "valor", required = true) String valor) throws DAOException;
}
