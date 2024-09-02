package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Chave;
import com.sudy.projeto.anotacoes.Coluna;
import com.sudy.projeto.anotacoes.Tabela;
import com.sudy.projeto.enums.StatusCliente;

@Tabela("cliente")
public class Cliente implements Persistente {

  @Coluna(dbName = "id", setJavaName = "setId")
  private Long id;

  @Coluna(dbName = "nome", setJavaName = "setNome")
  private String nome;

  @Chave("getCpf")
  @Coluna(dbName = "cpf", setJavaName = "setCpf")
  private String cpf;

  @Coluna(dbName = "telefone", setJavaName = "setTelefone")
  private String telefone;

  @Coluna(dbName = "endereco", setJavaName = "setEndereco")
  private String endereco;

  @Coluna(dbName = "numero", setJavaName = "setNumero")
  private String numero;

  @Coluna(dbName = "cidade", setJavaName = "setCidade")
  private String cidade;

  @Coluna(dbName = "estado", setJavaName = "setEstado")
  private String estado;

  @Coluna(dbName = "status", setJavaName = "setStatus")
  private StatusCliente status;

  public Cliente() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getCidade() {
    return cidade;
  }

  public void setCidade(String cidade) {
    this.cidade = cidade;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public StatusCliente getStatus() {
    return status;
  }

  public void setStatus(StatusCliente status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Cliente{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", cpf='" + cpf + '\'' +
            ", telefone='" + telefone + '\'' +
            ", endereco='" + endereco + '\'' +
            ", numero='" + numero + '\'' +
            ", cidade='" + cidade + '\'' +
            ", estado='" + estado + '\'' +
            ", status=" + status +
            '}';
  }
}
