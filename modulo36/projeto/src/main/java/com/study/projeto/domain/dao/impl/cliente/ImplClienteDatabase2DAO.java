package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase2DAO;
import com.study.projeto.domain.model.relational.Cliente;

public class ImplClienteDatabase2DAO extends ImplGenericDatabase2DAO<Cliente,Long> implements IClienteDAO {
  public ImplClienteDatabase2DAO() {
    super(Cliente.class);
  }
}
