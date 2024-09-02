package com.sudy.projeto.domain.model;

import com.sudy.projeto.anotacoes.Chave;
import com.sudy.projeto.anotacoes.Coluna;
import com.sudy.projeto.anotacoes.Tabela;
import com.sudy.projeto.domain.factory.ItemVendaFactory;
import com.sudy.projeto.enums.StatusVenda;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Tabela("venda")
public class Venda implements Persistente {

  @Coluna(dbName = "id", setJavaName = "setId")
  private Long id;

  @Chave("getCodigo")
  @Coluna(dbName = "codigo", setJavaName = "setCodigo")
  private String codigo;

  @Coluna(dbName = "id_cliente", setJavaName = "setCliente")
  private Cliente cliente;

  private Set<ItemVenda> itens;

  @Coluna(dbName = "valor_total", setJavaName = "setValorTotal")
  private BigDecimal valorTotal;

  @Coluna(dbName = "data_venda", setJavaName = "dataVenda")
  private Instant dataVenda;

  @Coluna(dbName = "status_venda", setJavaName = "setStatusVenda")
  private StatusVenda statusVenda;

  public Venda() {
    itens = new HashSet<>();
    valorTotal = BigDecimal.ZERO;
    statusVenda = StatusVenda.INICIADA;
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

  public void setStatusVenda(StatusVenda statusVenda) {
    this.statusVenda = statusVenda;
  }

  public Set<ItemVenda> getItens() {return itens;}

  public void setItens(Set<ItemVenda> itens) {
    if(!this.itens.isEmpty()){
      throw new UnsupportedOperationException("Operação não permitida");
    }
    this.itens = itens;
  }

  public void adicionarProduto(final Produto produto, final Integer quantidade) {
    validarStatus();
    Optional<ItemVenda> itemVenda =
        itens.stream()
            .filter(item -> item.getProduto().getCodigo().equals(produto.getCodigo()))
            .findAny();

    if (itemVenda.isPresent()) {
      ItemVenda item = itemVenda.get();
      item.adicionar(quantidade);
    } else {
      ItemVenda item = (ItemVenda) new ItemVendaFactory().create(produto, this, quantidade);
      itens.add(item);
    }
    recalcularTotalVenda();
  }

  public void removerProduto(final Produto produto, final Integer quantidade) throws SQLException {
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
  }

  private void recalcularTotalVenda() {
    BigDecimal valorTotal = BigDecimal.ZERO;
    for (ItemVenda item : itens) {
      valorTotal = valorTotal.add(item.getValorTotal());
    }
    this.valorTotal = valorTotal;
  }

  public void removerTodosItens() {
    validarStatus();
    itens.clear();
    valorTotal = BigDecimal.ZERO;
  }

  private void validarStatus() {
    if (this.statusVenda == StatusVenda.CONCLUIDA) {
      throw new UnsupportedOperationException("Não é permitido alterar venda concluída");
    }
  }

  public Integer getQuantidadeTotalProdutos(){
    int total = itens.stream().reduce(0
            ,(partialCount,item) -> partialCount + item.getQuantidade(), Integer::sum);
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
