package com.study.projeto.domain.model.relational;

import com.study.projeto.domain.builders.ItemVendaBuilder;
import com.study.projeto.domain.model.Persistente;
import com.study.projeto.enums.StatusRegistro;
import com.study.projeto.enums.StatusVenda;
import com.study.projeto.exceptions.AlteracaoStatusVendaException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "tb_venda")
public class Venda implements Persistente {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq")
  @SequenceGenerator(name = "venda_seq", sequenceName = "sq_venda", allocationSize = 1)
  private Long id;

  @Column(name = "codigo", nullable = false, unique = true)
  private String codigo;

  @ManyToOne
  @JoinColumn(
      name = "id_cliente",
      foreignKey = @ForeignKey(name = "fk_id_cliente"),
      referencedColumnName = "id",
      nullable = false)
  private Cliente cliente;

  @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ItemVenda> itens;

  @Column(name = "valor_total")
  private BigDecimal valorTotal;

  @Column(name = "data_venda", nullable = false)
  private Instant dataVenda;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusVenda statusVenda;

  @Column(name = "status_registro", nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusRegistro statusRegistro;

  public Venda() {
    itens = new HashSet<>();
    valorTotal = BigDecimal.ZERO;
    statusVenda = StatusVenda.INICIADA;
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

  public Cliente getCliente() {
    return cliente;
  }

  public void setCliente(Cliente cliente) {
    this.cliente = cliente;
  }

  public BigDecimal getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(BigDecimal valorTotal) {
    this.valorTotal = valorTotal;
  }

  public Instant getDataVenda() {
    return dataVenda;
  }

  public void setDataVenda(Instant dataVenda) {
    this.dataVenda = dataVenda;
  }

  public StatusVenda getStatusVenda() {
    return statusVenda;
  }

  public Set<ItemVenda> getItens() {
    return itens;
  }

  @Override
  public StatusRegistro getStatusRegistro() {
    return this.statusRegistro;
  }

  @Override
  public void setStatusRegistro(StatusRegistro status) {
    this.statusRegistro = status;
  }

  public void cancelarVenda() throws AlteracaoStatusVendaException {
    validarStatus();
    this.statusVenda = StatusVenda.CANCELADA;
  }

  public void concluirVenda() throws AlteracaoStatusVendaException {
    validarStatus();
    this.statusVenda = StatusVenda.CONCLUIDA;
  }

  public void setItens(Set<ItemVenda> itens) throws OperacaoNaoPermitidaException {
    if (!this.itens.isEmpty()) {
      throw new OperacaoNaoPermitidaException("Operação não permitida");
    }
    this.itens = itens;
    recalcularTotalVenda();
  }

  public void adicionarProduto(final Produto produto, final Integer quantidade)
      throws OperacaoNaoPermitidaException {
    try {
      validarStatus();
      Optional<ItemVenda> itemVenda =
          itens.stream()
              .filter(item -> item.getProduto().getCodigo().equals(produto.getCodigo()))
              .findFirst();

      if (itemVenda.isPresent()) {
        ItemVenda item = itemVenda.get();
        item.adicionar(quantidade);
      } else {
        ItemVenda item =
            ItemVendaBuilder.builder().produto(produto).venda(this).quantidade(quantidade).build();
        itens.add(item);
      }
      recalcularTotalVenda();
    } catch (AlteracaoStatusVendaException e) {
      throw new OperacaoNaoPermitidaException(
          "Não é permitido adicionar itens à venda concluída", e);
    }
  }

  public void removerProduto(final Produto produto)
          throws OperacaoNaoPermitidaException {
    try {
      validarStatus();
      Optional<ItemVenda> itemVenda =
              itens.stream()
                      .filter(item -> item.getProduto().getCodigo().equals(produto.getCodigo()))
                      .findAny();

      if (itemVenda.isPresent()) {
        ItemVenda item = itemVenda.get();
        itens.remove(item);
        recalcularTotalVenda();
      }
    } catch (AlteracaoStatusVendaException e) {
      throw new OperacaoNaoPermitidaException(
              "Não é permitido alterar os itens de venda concluída", e);
    }
  }

  public void removerProduto(final Produto produto, final Integer quantidade)
      throws OperacaoNaoPermitidaException {
    try {
      validarStatus();
      Optional<ItemVenda> itemVenda =
          itens.stream()
              .filter(item -> item.getProduto().getCodigo().equals(produto.getCodigo()))
              .findAny();

      if (itemVenda.isPresent()) {
        ItemVenda item = itemVenda.get();
        if (quantidade < item.getQuantidade()) {
          item.remover(quantidade);
        } else {
          itens.remove(item);
        }
        recalcularTotalVenda();
      }
    } catch (AlteracaoStatusVendaException e) {
      throw new OperacaoNaoPermitidaException(
          "Não é permitido alterar os itens de venda concluída", e);
    }
  }

  private void recalcularTotalVenda() {
    BigDecimal valorTotal = BigDecimal.ZERO;
    for (ItemVenda item : itens) {
      valorTotal = valorTotal.add(item.getValorTotal());
    }
    this.valorTotal = valorTotal;
  }

  public void removerTodosItens() throws OperacaoNaoPermitidaException {
    try {
      validarStatus();
      itens.clear();
      valorTotal = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);
    } catch (AlteracaoStatusVendaException e) {
      throw new OperacaoNaoPermitidaException(
          "Não é permitido remover itens de venda já concluída", e);
    }
  }

  private void validarStatus() throws AlteracaoStatusVendaException {
    if (this.statusVenda == StatusVenda.CONCLUIDA) {
      throw new AlteracaoStatusVendaException("Não é permitido alterar venda concluída");
    }
  }

  public Integer getQuantidadeTotalProdutos() {
    int total =
        itens.stream()
            .reduce(0, (partialCount, item) -> partialCount + item.getQuantidade(), Integer::sum);
    return total;
  }

  @Override
  public String toString() {
    return "Venda{"
        + "id="
        + id
        + ", codigo='"
        + codigo
        + '\''
        + ", idCliente="
        + cliente.getId()
        + ", valorTotal="
        + valorTotal
        + ", dataVenda="
        + dataVenda
        + ", statusVenda='"
        + statusVenda
        + '\''
        + '}';
  }
}
