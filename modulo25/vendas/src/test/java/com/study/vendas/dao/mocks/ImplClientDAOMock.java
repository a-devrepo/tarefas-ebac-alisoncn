package com.study.vendas.dao.mocks;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.domain.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ImplClientDAOMock implements IClienteDAO {

  private final List<Cliente> entities;

  public ImplClientDAOMock() {
    this.entities = new ArrayList<>();
  }

  @Override
  public Cliente salvar(Cliente cliente) {
    this.entities.add(cliente);
    return cliente;
  }

  @Override
  public Cliente buscarPorCpf(String cpf) {
    Cliente cliente = this.entities.stream()
            .filter(c -> c.getCpf().equals(cpf)).findFirst()
            .orElse(null);
    return cliente;
  }

  @Override
  public void excluir(String cpf) {
    this.entities
            .removeIf(cliente -> cliente.getCpf().equals(cpf));
  }
}
