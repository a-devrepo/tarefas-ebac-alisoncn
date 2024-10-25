package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.services.generic.ImplGenericService;

public class ImplClienteService extends ImplGenericService<Cliente, Long>
    implements IClienteService {
  public ImplClienteService(IGenericDAO dao) {
    super(dao);
  }
}
