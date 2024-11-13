package com.study.projeto.beans;

import com.study.projeto.domain.model.relational.Produto;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.RegistroNaoEncontradoException;
import com.study.projeto.services.impl.IProdutoService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {

    private static final long serialVersionUID = -5502494573808472205L;

    private Produto produto;

    private Collection<Produto> produtos;

    @Inject
    private IProdutoService produtoService;

    private Boolean isUpdate;

    @Inject
    private FacesContext context;

    @PostConstruct
    public void init() {
        try {
            this.isUpdate = false;
            criarProduto();
            this.produtos = produtoService.listar();
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    private void criarProduto() {
        this.produto = new Produto();
    }

    public void cancel() {
        try {
            this.isUpdate = false;
            this.produto = new Produto();
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void edit(Produto produto) {
        try {
            this.isUpdate = true;
            this.produto = produto;
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void delete(Produto produto) {
        try {
            produtoService.excluir(produto);
            produtos.remove(produto);
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produto removido com sucesso"));
        } catch (Exception e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void add() {
        try {
            produtoService.salvar(produto);
            this.produtos = produtoService.listar();
            criarProduto();
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produto cadastrado com sucesso"));
        } catch (DAOException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public void update() {
        try {
            produtoService.atualizar(this.produto);
            this.setIsUpdate(false);
            criarProduto();
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produto atualizado com sucesso"));
        } catch (DAOException | RegistroNaoEncontradoException e) {
            context.getCurrentInstance().addMessage("growl",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
        }
    }

    public String voltarTelaInicial() {
        return "/index.xhtml";
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Collection<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Collection<Produto> produtos) {
        this.produtos = produtos;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }
}
