package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Chave;
import com.sudy.projeto.anotacoes.Coluna;
import com.sudy.projeto.anotacoes.Tabela;
import java.math.BigDecimal;

@Tabela("item_venda")
public class ItemVenda implements Persistente {

  @Chave("getId")
  @Coluna(dbName = "id", setJavaName = "setId")
  private Long id;

  @Coluna(dbName = "id_produto", setJavaName = "setProduto")
  private Produto produto;

  @Coluna(dbName = "id_venda", setJavaName = "setVenda")
  private Venda venda;

  @Coluna(dbName = "quantidade", setJavaName = "setQuantidade")
  private Integer quantidade;

  @Coluna(dbName = "valor_total", setJavaName = "setValorTotal")
  private BigDecimal valorTotal;

  public ItemVenda() {
    quantidade = 0;
    valorTotal = BigDecimal.ZERO;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public Venda getVenda() {
    return venda;
  }

  public void setVenda(Venda venda) {
    this.venda = venda;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public void adicionar(final Integer quantidade) {
    this.quantidade += quantidade;
    BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    this.valorTotal = this.valorTotal.add(novoValor);
  }

  public void remover(final Integer quantidade) {
    this.quantidade -= quantidade;
    BigDecimal novoValor = this.produto.getValor().multiply(BigDecimal.valueOf(quantidade));
    this.valorTotal = this.valorTotal.subtract(novoValor);
  }

  @Override
  public String toString() {
    return "ItemVenda{"
        + "id="
        + id
        + ", idProduto="
        + produto.getId()
        + ", idVenda="
        + venda.getId()
        + ", quantidade="
        + quantidade
        + ", valorTotal="
        + valorTotal
        + '}';
  }
}
