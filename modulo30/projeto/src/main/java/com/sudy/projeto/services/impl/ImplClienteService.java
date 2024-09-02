package com.sudy.projeto.services.impl;

import com.sudy.projeto.domain.dao.generic.IGenericDAO;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.services.generic.ImplGenericService;

public class ImplClienteService extends ImplGenericService<Cliente,String> implements IClienteService  {
    public ImplClienteService(IGenericDAO dao) {
        super(dao);
    }
}
