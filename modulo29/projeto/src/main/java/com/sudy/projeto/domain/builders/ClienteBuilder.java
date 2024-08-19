package com.sudy.projeto.domain.builders;

import com.sudy.projeto.domain.model.Cliente;

public class ClienteBuilder {

  private Cliente cliente;

  private ClienteBuilder() {
    this.cliente = new Cliente();
  }

  public static ClienteBuilder builder() {
    return new ClienteBuilder();
  }

  public ClienteBuilder codigo(Long codigo) {
    this.cliente.setCodigo(codigo);
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

  public ClienteBuilder sobrenome(String sobrenome) {
    this.cliente.setSobreNnome(sobrenome);
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

  public Cliente build() {
    return this.cliente;
  }
}
