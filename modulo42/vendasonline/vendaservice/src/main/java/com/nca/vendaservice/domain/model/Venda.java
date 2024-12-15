package com.nca.vendaservice.domain.model;


import com.nca.vendaservice.enums.StatusRegistro;
import com.nca.vendaservice.enums.StatusVenda;
import com.nca.vendaservice.exceptions.AlteracaoStatusVendaException;
import com.nca.vendaservice.exceptions.OperacaoNaoPermitidaException;
import com.nca.vendaservice.response.Cliente;
import com.nca.vendaservice.response.Produto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Document(collection = "venda")
@Builder
@Schema(name = "Venda", description = "Venda")
public class Venda {

    @Transient
    public static final String SEQUENCE_NAME = "venda_sequence";

    @Id
    @Schema(description = "Identificador único")
    private String id;

    @NotNull
    @Size(min = 1, max = 50)
    @Schema(description = "codigo", minLength = 1, maxLength = 50, nullable = false)
    private String codigo;

    @Schema(description = "Cliente")
    private Cliente cliente;

    @DBRef(lazy = true)
    private Set<ItemVenda> itens;

    @NotNull
    @Schema(description = "valor", nullable = false)
    @Field(name = "valor_total")
    private BigDecimal valorTotal;

    @Schema(description = "Data Venda")
    @Field(name = "data_venda")
    private Instant dataVenda;

    @Schema(description = "Status Venda")
    @Field(name = "status_venda")
    private StatusVenda statusVenda;

    @Schema(description = "Status Registro")
    @Field(name = "status_registro")
    private StatusRegistro statusRegistro;

    public Venda() {
        this.statusRegistro = StatusRegistro.ATIVO;
        this.statusVenda = StatusVenda.INICIADA;
    }

    public Venda(String id, String codigo, Cliente cliente, Set<ItemVenda> itens
            , BigDecimal valorTotal, Instant dataVenda, StatusVenda statusVenda, StatusRegistro statusRegistro) {
        this.id = id;
        this.codigo = codigo;
        this.cliente = cliente;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.dataVenda = dataVenda;
        this.statusVenda = statusVenda;
        this.statusRegistro = statusRegistro;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Set<ItemVenda> getItens() {
        return itens;
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

    public StatusRegistro getStatusRegistro() {
        return statusRegistro;
    }

    public void setStatusRegistro(StatusRegistro statusRegistro) {
        this.statusRegistro = statusRegistro;
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
                        ItemVenda.builder().produto(produto).venda(this).quantidade(quantidade).build();
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
                        .reduce(0
                                , (partialCount, item) ->
                                        partialCount + item.getQuantidade(), Integer::sum);
        return total;
    }
}