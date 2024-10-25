package com.study.projeto.domain.model.nonrelational;

import com.study.projeto.domain.model.Persistente;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.enums.StatusRegistro;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;


@Entity
public class ClienteMD implements Persistente {

  @Id
  private ObjectId id;

  private String nome;

  private String cpf;

  private String telefone;

  private StatusCliente status;

  private StatusRegistro statusRegistro;

  public ClienteMD() {
    this.statusRegistro = StatusRegistro.ATIVO;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public StatusCliente getStatus() {
    return status;
  }

  public void setStatus(StatusCliente status) {
    this.status = status;
  }

  @Override
  public StatusRegistro getStatusRegistro() {
    return null;
  }

  @Override
  public void setStatusRegistro(StatusRegistro status) {}

  @Override
  public String toString() {
    return "Cliente{"
        + "id="
        + id
        + ", nome='"
        + nome
        + '\''
        + ", cpf='"
        + cpf
        + '\''
        + ", telefone='"
        + telefone
        + '\''
        + ", status="
        + status
        + ", statusRegistro="
        + statusRegistro
        + '}';
  }
}
