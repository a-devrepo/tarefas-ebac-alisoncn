package com.nca.vendaservice.response;


import com.nca.vendaservice.enums.StatusRegistro;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Produto {

    private String id;

    private String codigo;

    private String nome;

    private String descricao;

    private String fabricante;

    private BigDecimal valor;

    private StatusRegistro statusRegistro;

    public Produto() {
        this.statusRegistro = StatusRegistro.ATIVO;
    }

    public Produto(String id, String codigo, String nome, String descricao
            , String fabricante, BigDecimal valor, StatusRegistro statusRegistro) {
        super();
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.valor = valor;
        this.statusRegistro = statusRegistro;
    }
}

