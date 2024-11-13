package com.study.projeto.services.impl;

import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.services.generic.IGenericService;

import java.util.Collection;

public interface IClienteService extends IGenericService<Cliente, Long> {
    public Collection<Cliente> filtrarClientesPor(String param, String value) throws DAOException;
}
