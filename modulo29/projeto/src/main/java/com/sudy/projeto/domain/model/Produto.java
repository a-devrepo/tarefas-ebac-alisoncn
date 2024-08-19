package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Codigo;
import com.sudy.projeto.anotacoes.Entidade;
import java.math.BigDecimal;

@Entidade("produto")
public class Produto {

  @Codigo("getCodigo")
  private Long codigo;

  private String nome;

  private String fabricante;

  private BigDecimal valor;

  public Produto() {}

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getFabricante() {
    return fabricante;
  }

  public void setFabricante(String fabricante) {
    this.fabricante = fabricante;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  @Override
  public String toString() {
    return "Produto{"
        + "codigo="
        + codigo
        + ", nome='"
        + nome
        + '\''
        + ", fabricante='"
        + fabricante
        + '\''
        + ", valor="
        + valor
        + '}';
  }
}
