package com.study.vendas.dao.mocks;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.domain.Cliente;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImplClientDAOMock implements IClienteDAO {

  private final List<Cliente> entities;

  public ImplClientDAOMock() {
    this.entities = new ArrayList<>();
  }

  @Override
  public Collection<Cliente> listar() {
    return entities;
  }

  @Override
  public Cliente salvar(Cliente cliente) {
    this.entities.add(cliente);
    return cliente;
  }

  @Override
  public Cliente buscar(Long codigo) throws RegistroNaoEncontradoException {
      return this.entities.stream()
            .filter(c -> c.getCodigo().equals(codigo)).findFirst()
            .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
  }

  @Override
  public void excluir(Long code) throws RegistroNaoEncontradoException {
      this.buscar(code);
      this.entities.removeIf(c -> c.getCodigo().equals(code));
  }

  @Override
  public void atualizar(Cliente cliente) throws RegistroNaoEncontradoException {
    Cliente clienteOld = buscar(cliente.getCodigo());
    alterar(cliente,clienteOld);
  }

  private void alterar(Cliente cliente, Cliente target) {
    target.setNome(cliente.getNome());
    target.setEndereco(cliente.getEndereco());
    target.setCidade(cliente.getCidade());
    target.setNumero(cliente.getNumero());
    target.setEstado(cliente.getEstado());
    target.setTelefone(cliente.getTelefone());
  }
}
