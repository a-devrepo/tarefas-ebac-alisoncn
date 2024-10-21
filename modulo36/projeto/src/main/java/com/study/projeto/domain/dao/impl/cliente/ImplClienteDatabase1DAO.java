package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.ImplGenericDatabase1DAO;
import com.study.projeto.domain.model.Cliente;

public class ImplClienteDatabase1DAO extends ImplGenericDatabase1DAO<Cliente,Long>implements IClienteDAO {
  public ImplClienteDatabase1DAO() {
    super(Cliente.class);
  }
}
