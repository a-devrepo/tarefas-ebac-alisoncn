package com.nca.vendaservice.domain.model;

import com.nca.vendaservice.enums.StatusRegistro;
import com.nca.vendaservice.response.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Builder
@Document(collection = "item_venda")
@Schema(name = "Item Venda", description = "Item Venda")
public class ItemVenda {

    @Transient
    public static final String SEQUENCE_NAME = "item_venda_sequence";

    @Id
    @Schema(description = "Identificador único")
    private String id;

    @DBRef
    @NotNull
    @Schema(description = "Produto")
    private Produto produto;

    @DBRef
    @NotNull
    @Schema(description = "Venda")
    private Venda venda;

    @NotNull
    @Schema(description = "Quantidade")
    private Integer quantidade;

    @NotNull
    @Schema(description = "Valor Total")
    private BigDecimal valorTotal;

    @Schema(description = "Status Registro")
    @Field(name = "status_registro")
    private StatusRegistro statusRegistro;

    public ItemVenda() {
        quantidade = 0;
        valorTotal = BigDecimal.ZERO;
        this.statusRegistro = StatusRegistro.ATIVO;
    }

    public ItemVenda(String id, Produto produto, Venda venda
            , Integer quantidade, BigDecimal valorTotal, StatusRegistro statusRegistro) {
        this.id = id;
        this.produto = produto;
        this.venda = venda;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.statusRegistro = statusRegistro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}

