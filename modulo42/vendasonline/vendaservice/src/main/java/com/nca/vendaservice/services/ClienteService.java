package com.nca.vendaservice.services;

import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.proxy.ClienteProxy;
import com.nca.vendaservice.response.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClienteService {

    @Autowired
    ClienteProxy proxy;

    public Collection<Cliente> filtrarClientePor(
            String campo
            , String valor) throws DAOException {
        ResponseEntity<Collection<Cliente>> response = proxy.filtrarClientesPor(campo, valor);
        Collection<Cliente> list = response.getBody();
        return list;
    }
}
