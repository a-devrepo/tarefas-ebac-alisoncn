package com.study.projeto.domain.model.relational;

import com.study.projeto.domain.model.Persistente;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.enums.StatusRegistro;

import javax.persistence.*;

@Entity
@Table(name = "tb_cliente")
public class Cliente implements Persistente {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
  @SequenceGenerator(name = "cliente_seq", sequenceName = "sq_cliente", allocationSize = 1)
  private Long id;

  @Column(name = "nome", nullable = false, length = 50)
  private String nome;

  @Column(name = "cpf", nullable = false, unique = true)
  private String cpf;

  @Column(name = "telefone", nullable = false)
  private String telefone;

  @Column(name = "email", nullable = false)
  private String email;

  @Embedded
  private Endereco endereco;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusCliente status;

  @Column(name="status_registro",nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusRegistro statusRegistro;


  public Cliente() {
    this.statusRegistro = StatusRegistro.ATIVO;
    this.status = StatusCliente.ATIVO;
  }

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

  public Endereco getEndereco() {
    return endereco;
  }

  public void setEndereco(Endereco endereco) {
    this.endereco = endereco;
  }

  public StatusCliente getStatus() {
    return status;
  }

  public void setStatus(StatusCliente status) {
    this.status = status;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public StatusRegistro getStatusRegistro() {
    return this.statusRegistro;
  }

  @Override
  public void setStatusRegistro(StatusRegistro status) {
    this.statusRegistro = status;
  }



  @Override
  public String toString() {
    return "Cliente{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", cpf='" + cpf + '\'' +
            ", telefone='" + telefone + '\'' +
            ", status=" + status +
            ", statusRegistro=" + statusRegistro +
            '}';
  }
}
