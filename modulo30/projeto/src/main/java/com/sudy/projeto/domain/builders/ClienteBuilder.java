package com.sudy.projeto.domain.builders;

import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.enums.StatusCliente;

public class ClienteBuilder {

  private Cliente cliente;

  private ClienteBuilder() {
    this.cliente = new Cliente();
  }

  public static ClienteBuilder builder() {
    return new ClienteBuilder();
  }

  public ClienteBuilder id(Long codigo) {
    this.cliente.setId(codigo);
    return this;
  }

  public ClienteBuilder cpf(String cpf) {
    this.cliente.setCpf(cpf);
    return this;
  }

  public ClienteBuilder nome(String nome) {
    this.cliente.setNome(nome);
    return this;
  }

  public ClienteBuilder telefone(String telefone) {
    this.cliente.setTelefone(telefone);
    return this;
  }

  public ClienteBuilder endereco(String endereco) {
    this.cliente.setEndereco(endereco);
    return this;
  }

  public ClienteBuilder cidade(String cidade) {
    this.cliente.setCidade(cidade);
    return this;
  }

  public ClienteBuilder estado(String estado) {
    this.cliente.setEstado(estado);
    return this;
  }

  public ClienteBuilder numero(String numero) {
    this.cliente.setNumero(numero);
    return this;
  }

  public ClienteBuilder status(StatusCliente statusCliente) {
    this.cliente.setStatus(statusCliente);
    return this;
  }

  public Cliente build() {
    return this.cliente;
  }
}
