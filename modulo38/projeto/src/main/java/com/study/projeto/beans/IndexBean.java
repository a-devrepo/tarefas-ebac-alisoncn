package com.study.projeto.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class IndexBean implements Serializable {

    private static final long serialVersionUID = -8931918527853796110L;

    public String redirectCliente() {
        return "/cliente/list.xhtml";
    }

    public String redirectProduto() {
        return "/produto/list.xhtml";
    }

    public String redirectVenda() {
        return "/venda/list.xhtml";
    }
}
