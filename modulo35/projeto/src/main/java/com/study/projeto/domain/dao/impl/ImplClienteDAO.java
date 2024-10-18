package com.study.projeto.domain.dao.impl;

import com.study.projeto.domain.dao.generic.ImplGenericDAO;
import com.study.projeto.domain.model.Cliente;

public class ImplClienteDAO extends ImplGenericDAO<Cliente, Long> implements IClienteDAO {
  public ImplClienteDAO() {
    super(Cliente.class);
  }
}
