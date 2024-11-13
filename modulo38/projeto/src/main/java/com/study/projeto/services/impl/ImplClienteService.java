package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.impl.cliente.IClienteDAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.services.generic.ImplGenericService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ImplClienteService extends ImplGenericService<Cliente, Long> implements IClienteService {


    @Inject
    public ImplClienteService(IClienteDAO dao) {
        super(dao);
    }

    @Override
    public Collection<Cliente> filtrarClientesPor(String param, String value) throws DAOException {
        return ((IClienteDAO)dao).findBy(param, value);
    }
}
