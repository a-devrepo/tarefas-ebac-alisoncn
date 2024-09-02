package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Chave;
import com.sudy.projeto.anotacoes.Coluna;
import com.sudy.projeto.anotacoes.Tabela;
import java.math.BigDecimal;

@Tabela("produto")
public class Produto implements Persistente{

  @Chave("getId")
  @Coluna(dbName = "id", setJavaName = "setId")
  private Long id;

  @Coluna(dbName = "codigo", setJavaName = "setCodigo")
  private String codigo;

  @Coluna(dbName = "nome", setJavaName = "setNome")
  private String nome;

  @Coluna(dbName = "descricao", setJavaName = "setDescricao")
  private String descricao;

  @Coluna(dbName = "fabricante", setJavaName = "setFabricante")
  private String fabricante;

  @Coluna(dbName = "valor", setJavaName = "setValor")
  private BigDecimal valor;

  public Produto() {}

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
  public String toString() {
    return "Produto{" +
            "id=" + id +
            ", codigo='" + codigo + '\'' +
            ", nome='" + nome + '\'' +
            ", descricao='" + descricao + '\'' +
            ", fabricante='" + fabricante + '\'' +
            ", valor=" + valor +
            '}';
  }
}
