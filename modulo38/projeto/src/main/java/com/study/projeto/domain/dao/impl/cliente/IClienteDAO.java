package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.exceptions.DAOException;

import java.util.Collection;

public interface IClienteDAO extends IGenericDAO<Cliente, Long> {
    public Collection<Cliente> findBy(String param, String value) throws DAOException;
}
