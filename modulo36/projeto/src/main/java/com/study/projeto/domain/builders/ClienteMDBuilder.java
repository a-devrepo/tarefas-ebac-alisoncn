package com.study.projeto.domain.builders;

import com.study.projeto.domain.model.nonrelational.ClienteMD;
import com.study.projeto.enums.StatusCliente;
import org.bson.types.ObjectId;

public class ClienteMDBuilder {

  private ClienteMD cliente;

  private ClienteMDBuilder() {
    this.cliente = new ClienteMD();
  }

  public static ClienteMDBuilder builder() {
    return new ClienteMDBuilder();
  }

  public ClienteMDBuilder id(ObjectId codigo) {
    this.cliente.setId(codigo);
    return this;
  }

  public ClienteMDBuilder cpf(String cpf) {
    this.cliente.setCpf(cpf);
    return this;
  }

  public ClienteMDBuilder nome(String nome) {
    this.cliente.setNome(nome);
    return this;
  }

  public ClienteMDBuilder telefone(String telefone) {
    this.cliente.setTelefone(telefone);
    return this;
  }

  public ClienteMDBuilder status(StatusCliente statusCliente) {
    this.cliente.setStatus(statusCliente);
    return this;
  }

  public ClienteMD build() {
    return this.cliente;
  }
}
