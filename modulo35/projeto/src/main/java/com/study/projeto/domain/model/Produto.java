package com.study.projeto.domain.model;

import com.study.projeto.enums.StatusRegistro;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "tb_produto")
public class Produto implements Persistente {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq")
  @SequenceGenerator(name = "produto_seq", sequenceName = "sq_produto", allocationSize = 1)
  private Long id;

  @Column(name = "codigo", nullable = false, unique = true)
  private String codigo;

  @Column(name = "nome", nullable = false, length = 100)
  private String nome;

  @Column(name = "descricao", nullable = false, length = 100)
  private String descricao;

  @Column(name = "fabricante", nullable = false, length = 100)
  private String fabricante;

  @Column(name = "valor", nullable = false)
  private BigDecimal valor;

  @Column(name = "status_registro",nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusRegistro statusRegistro;

  public Produto() {
    this.statusRegistro = StatusRegistro.ATIVO;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getFabricante() {
    return fabricante;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
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
    return "Produto{"
        + "id="
        + id
        + ", codigo='"
        + codigo
        + '\''
        + ", nome='"
        + nome
        + '\''
        + ", descricao='"
        + descricao
        + '\''
        + ", fabricante='"
        + fabricante
        + '\''
        + ", valor="
        + valor
        + '}';
  }
}
