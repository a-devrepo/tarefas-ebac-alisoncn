package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase3DAO;
import com.study.projeto.domain.model.Cliente;

public class ImplClienteDatabase3DAO extends ImplGenericDatabase3DAO<Cliente,Long> implements IClienteDAO {
  public ImplClienteDatabase3DAO() {
    super(Cliente.class);
  }
}
