package com.study.projeto.beans;

import com.study.projeto.domain.model.relational.Cliente;
import com.study.projeto.domain.model.relational.ItemVenda;
import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.domain.model.relational.Venda;
import com.study.projeto.exceptions.AlteracaoStatusVendaException;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import com.study.projeto.services.impl.IClienteService;
import com.study.projeto.services.impl.IProdutoService;
import com.study.projeto.services.impl.IVendaService;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Named
@ViewScoped
public class VendaBean implements Serializable {

    private static final long serialVersionUID = 6451468674745455212L;

    private Venda venda;

    private Collection<Venda> vendas;

    @Inject
    private IVendaService vendaService;

    @Inject
    private IClienteService clienteService;

    @Inject
    private IProdutoService produtoService;

    private Boolean isUpdate;

    private LocalDate dataVenda;

    private Integer quantidadeProduto;

    private Set<ItemVenda> produtos;

    private Produto produtoSelecionado;

    private BigDecimal valorTotal;

    @Inject
    private FacesContext context;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            this.venda = new Venda();
            this.produtos = new HashSet<>();
            this.vendas = vendaService.listar();
            this.valorTotal = BigDecimal.ZERO;
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void cancel() {
        try {
            this.isUpdate = false;
            this.venda = new Venda();
            this.produtos = new HashSet<>();
            this.valorTotal = BigDecimal.ZERO;
            this.dataVenda = null;
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void add() {
        try {
            venda.setDataVenda(dataVenda.atStartOfDay(ZoneId.systemDefault()).toInstant());
            vendaService.salvar(venda);
            this.vendas = vendaService.listar();
            cancel();
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Venda adicionada com sucesso"));
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void edit(Venda venda) {
        try {
            this.isUpdate = true;
            this.venda = this.vendaService.buscarVendaItens(venda);
            this.dataVenda = LocalDate.ofInstant(this.venda.getDataVenda(), ZoneId.systemDefault());
            this.produtos = this.venda.getItens();
            //this.venda.recalcularValorTotalVenda();
            this.valorTotal = this.venda.getValorTotal();
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage("Erro ao tentar editar a venda"));
        }

    }

    public void delete(Venda venda) {
        try {
            vendaService.cancelarVenda(venda);
            cancel();
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Venda cancelada com sucesso"));
        } catch (DAOException | AlteracaoStatusVendaException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void finalizar(Venda venda) {
        try {
            vendaService.concluirVenda(venda);
            cancel();
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Venda concluída com sucesso"));
        } catch (DAOException | AlteracaoStatusVendaException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void update() {
        try {
            vendaService.atualizar(this.venda);
            this.vendas = vendaService.listar();
            cancel();
            context.getCurrentInstance().addMessage("growl", new FacesMessage("Venda atualizada com sucesso"));
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }

    }

    public List<Cliente> filtrarClientes(String query) {
        try {
            List<Cliente> clientes = (List<Cliente>) this.clienteService.filtrarClientesPor("nome", query);
            cancel();
            return clientes;
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
        return null;
    }


    public List<Produto> filtrarProdutos(String query) {
        try {
            List<Produto> produtos = (List<Produto>) this.produtoService.filtrarProdutosPor("nome", query);
            cancel();
            return produtos;
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
        return null;
    }

    public void adicionarProduto() {
        try {
            Optional<ItemVenda> itemVenda =
                    this.venda.getItens().stream()
                            .filter(prodF -> prodF.getProduto()
                                    .getCodigo().equals(this.produtoSelecionado.getCodigo())).findFirst();

            if (itemVenda.isPresent()) {
                ItemVenda item = itemVenda.get();
                item.adicionar(this.quantidadeProduto);
            } else {
                ItemVenda item = new ItemVenda();
                item.setProduto(this.produtoSelecionado);
                item.adicionar(this.quantidadeProduto);
                item.setVenda(this.venda);
                this.venda.adicionarProduto(this.produtoSelecionado, this.quantidadeProduto);
            }
            this.produtos = this.venda.getItens();
            this.valorTotal = this.venda.getValorTotal();
        } catch (OperacaoNaoPermitidaException e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
    }

    public void removerProduto() {
        try {
            Optional<ItemVenda> item =
                    this.venda.getItens().stream()
                            .filter(prodF -> prodF.getProduto().getCodigo()
                                    .equals(this.produtoSelecionado.getCodigo())).findFirst();

            if (item.isPresent()) {
                ItemVenda itemVenda = item.get();
                itemVenda.remover(this.quantidadeProduto);
                if (itemVenda.getQuantidade() == 0 || itemVenda.getQuantidade() < 0) {
                    this.venda.removerProduto(produtoSelecionado);
                }
                this.produtos = this.venda.getItens();
                this.valorTotal = this.venda.getValorTotal();
            }

        } catch (OperacaoNaoPermitidaException e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
    }

    public void removerProduto(ItemVenda itemVenda) {
        try {
            this.venda.removerProduto(produtoSelecionado);
            this.produtos = this.venda.getItens();
            this.valorTotal = this.venda.getValorTotal();
        } catch (OperacaoNaoPermitidaException e) {
            context.getCurrentInstance().addMessage("growl", new FacesMessage(e.getMessage()));
        }
    }

    public void onRowEdit(RowEditEvent<ItemVenda> event) {
        ItemVenda item = (ItemVenda) event.getObject();
        adicionarOuRemoverProduto(item);
    }

    public void onRowCancel(RowEditEvent<ItemVenda> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        context.getCurrentInstance().addMessage(null, msg);
    }

    public void adicionarOuRemoverProduto(ItemVenda itemVenda) {
        if (itemVenda.getQuantidade() != this.quantidadeProduto) {
            int quantidade = this.quantidadeProduto - itemVenda.getQuantidade();
            if (quantidade > 0) {
                itemVenda.adicionar(quantidade);
            } else {
                this.produtos.remove(itemVenda);
            }
            this.valorTotal = BigDecimal.ZERO;
            this.produtos.forEach(pro -> {
                this.valorTotal = this.valorTotal.add(pro.getValorTotal());
            });
        }
    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Collection<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(Collection<Venda> vendas) {
        this.vendas = vendas;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Set<ItemVenda> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ItemVenda> produtos) {
        this.produtos = produtos;
    }

    public Integer getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(Integer quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
