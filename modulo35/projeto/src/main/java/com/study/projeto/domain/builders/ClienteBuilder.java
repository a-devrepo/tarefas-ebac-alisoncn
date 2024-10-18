package com.study.projeto.domain.builders;

import com.study.projeto.domain.model.Cliente;
import com.study.projeto.domain.model.Endereco;
import com.study.projeto.enums.StatusCliente;

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

  public ClienteBuilder endereco(Endereco endereco) {
    this.cliente.setEndereco(endereco);
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
