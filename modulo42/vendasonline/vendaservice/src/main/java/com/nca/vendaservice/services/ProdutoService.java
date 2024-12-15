package com.nca.vendaservice.services;

import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.proxy.ClienteProxy;
import com.nca.vendaservice.proxy.ProdutoProxy;
import com.nca.vendaservice.response.Cliente;
import com.nca.vendaservice.response.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProdutoService {

    @Autowired
    ProdutoProxy proxy;

    public Collection<Produto> filtrarClientesPor(
            String campo
            , String valor) throws DAOException {
        ResponseEntity<Collection<Produto>> response = proxy.filtrarProdutoPor(campo, valor);
        Collection<Produto> list = response.getBody();
        return list;
    }
}
