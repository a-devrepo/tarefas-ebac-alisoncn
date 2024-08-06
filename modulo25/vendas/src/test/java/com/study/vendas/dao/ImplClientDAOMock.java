package com.study.vendas.dao;

import com.study.vendas.domain.Cliente;

public class ImplClientDAOMock implements IClienteDAO {

  @Override
  public Cliente salvar(Cliente cliente) {
    return cliente;
  }

  @Override
  public Cliente buscarPorCpf(String cpf) {
    Cliente cliente = new Cliente();
    cliente.setCpf(cpf);
    return cliente;
  }
}
