package com.study.projeto.domain.model;

import com.study.projeto.enums.StatusRegistro;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "tb_item_venda")
public class ItemVenda implements Persistente {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_venda_seq")
  @SequenceGenerator(name = "item_venda_seq", sequenceName = "sq_item_venda", allocationSize = 1)
  private Long id;

  @ManyToOne
  @JoinColumn(
      name = "id_produto",
      foreignKey = @ForeignKey(name = "fk_item_venda"),
      referencedColumnName = "id",
      nullable = false)
  private Produto produto;

  @ManyToOne
  @JoinColumn(
      name = "id_venda",
      foreignKey = @ForeignKey(name = "fk_item_produto"),
      referencedColumnName = "id",
      nullable = false)
  private Venda venda;

  @Column(nullable = false)
  private Integer quantidade;

  @Column(name = "valor_total", nullable = false)
  private BigDecimal valorTotal;

  @Column(name = "status_registro", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusRegistro statusRegistro;

  public ItemVenda() {
    quantidade = 0;
    valorTotal = BigDecimal.ZERO;
    this.statusRegistro = StatusRegistro.ATIVO;
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
    this.adicionar(quantidade);
    this.quantidade = quantidade;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  @Override
  public StatusRegistro getStatusRegistro() {
    return this.statusRegistro;
  }

  @Override
  public void setStatusRegistro(StatusRegistro status) {
    this.statusRegistro = status;
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
