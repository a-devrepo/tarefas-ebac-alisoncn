package com.sudy.projeto.services.impl;

import static org.junit.Assert.*;

import com.sudy.projeto.domain.dao.impl.*;
import com.sudy.projeto.domain.factory.ClienteFactory;
import com.sudy.projeto.domain.factory.ProdutoFactory;
import com.sudy.projeto.domain.factory.VendaFactory;
import com.sudy.projeto.domain.model.Cliente;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.domain.model.Venda;
import com.sudy.projeto.enums.StatusCliente;
import com.sudy.projeto.enums.StatusVenda;
import com.sudy.projeto.exceptions.DAOException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestJdbc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collection;

public class IVendaServiceTest {

    private IVendaService service;
    private ImplVendaDAO dao;
    private IClienteDAO clienteDAO;
    private IProdutoDAO produtoDAO;
    private Cliente cliente;
    private Produto produto;

    public IVendaServiceTest(){
        dao = new ImplVendaDAO();
        service = new ImplVendaService(dao);
        clienteDAO = new ImplClienteDAO();
        produtoDAO = new ImplProdutoDAO();
    }

    @Before
    public void init() throws DAOException {
        this.cliente = cadastrarCliente();
        this.produto = cadastrarProduto();
    }

    @After
    public void end() throws SQLException, DAOException {
        excluirItemsVenda();
        excluirVendas();
        excluirProdutos();
        clienteDAO.excluir(this.cliente.getCpf());
    }

    @Test
    public void salvarVendaTest() throws DAOException {
        Venda venda = criarVenda("TH863");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);
    }

    @Test
    public void salvarVendaComCodigoDuplicadoTest() throws DAOException {
        String codigoVenda = "TH863";
        Venda venda = criarVenda(codigoVenda);
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);
        String mensagemErroEsperada = "Já existe registro com o código: " + codigoVenda;

        Venda novaVenda = criarVenda(codigoVenda);

        DAOException daoException = assertThrows(DAOException.class, () -> service.salvar(novaVenda));
        Assert.assertEquals(mensagemErroEsperada, daoException.getMessage());
    }

    @Test
    public void consultarVendaTest() throws DAOException, RegistroNaoEncontradoException {
        Venda venda = criarVenda("TH863");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());
        Assert.assertEquals(new BigDecimal("6000.00"), vendaCadastrada.getValorTotal());
        Assert.assertEquals(StatusVenda.INICIADA, vendaCadastrada.getStatusVenda());
    }

    @Test
    public void cancelarVendaTest() throws DAOException, RegistroNaoEncontradoException {
        Venda venda = criarVenda("TH863");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());

        service.cancelarVenda(vendaCadastrada);

        vendaCadastrada = service.buscar(venda.getCodigo());

        Assert.assertEquals(StatusVenda.CANCELADA.name(), vendaCadastrada.getStatusVenda().name());
    }

    @Test
    public void finalizarVendaTest() throws DAOException, RegistroNaoEncontradoException {
        Venda venda = criarVenda("JS83X");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());

        service.concluirVenda(vendaCadastrada);

        vendaCadastrada = service.buscar(venda.getCodigo());

        Assert.assertEquals(StatusVenda.CONCLUIDA.name(), vendaCadastrada.getStatusVenda().name());
    }

    @Test
    public void aumentarQuantidadedeProdutoDaVenda()
            throws DAOException, RegistroNaoEncontradoException {
        Integer quantidadeProdutosEsperada = 2;
        Integer quantidadeAdicional = 1;
        Venda venda = criarVenda("JS83X");
        BigDecimal valorTotalEsperado = new BigDecimal("12000.00");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());

        vendaCadastrada.adicionarProduto(produto, quantidadeAdicional);

        service.alterarQuantidadeProdutosVenda(vendaCadastrada, produto.getCodigo());

        vendaCadastrada = service.buscar(venda.getCodigo());

        Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
        Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
    }

    @Test
    public void cadastrarProdutoDiferenteAVendaTeste()
            throws DAOException, RegistroNaoEncontradoException {
        Integer quantidadeProdutosEsperada = 2;
        String codigoVenda = "KDSA82";
        String codigoProduto = "DXIE9832";
        Integer quantidadeProdutoNovo = 1;
        BigDecimal valorTotalEsperado = new BigDecimal("9000.00");

        Venda venda = criarVenda(codigoVenda);
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Produto produtoDiferente =
                cadastrarProduto(
                        codigoProduto, "Monitor LG", "Monitor Wide 25", "LG",
                        new BigDecimal("3000.00"));

        Venda vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.adicionarProduto(produtoDiferente, quantidadeProdutoNovo);

        service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoDiferente, quantidadeProdutoNovo);

        vendaCadastrada = service.buscar(codigoVenda);

        Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
        Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
    }

    @Test
    public void removerProdutoVendaTeste()
            throws DAOException, RegistroNaoEncontradoException, SQLException {
        Integer quantidadeProdutosEsperada = 1;
        String codigoVenda = "KDSA82";
        String codigoProduto = "DXIE9832";
        Integer quantidadeProdutoNovo = 1;
        BigDecimal valorTotalEsperado = new BigDecimal("3000.00");
        Integer quantidadeARemover = 1;

        Venda venda = criarVenda(codigoVenda);
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Produto produtoNovo =
                cadastrarProduto(
                        codigoProduto, "Monitor LG", "Monitor Wide 25", "LG",
                        new BigDecimal("3000.00"));

        Venda vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.adicionarProduto(produtoNovo, quantidadeProdutoNovo);

        service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

        vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.removerProduto(this.produto, quantidadeARemover);

        service.removerProdutoDeVenda(vendaCadastrada, this.produto, quantidadeARemover);

        vendaCadastrada = service.buscar(codigoVenda);

        Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
        Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
    }

    @Test
    public void removerUmaUnidadeDeProdutoVendaTeste()
            throws DAOException, RegistroNaoEncontradoException, SQLException {
        Integer quantidadeProdutosEsperada = 3;
        String codigoVenda = "KDSA82";
        String codigoProduto = "DXIE9832";
        Integer quantidadeProdutoNovo = 3;
        BigDecimal valorTotalEsperado = new BigDecimal("12000.00");
        Integer quantidadeARemover = 1;

        Venda venda = criarVenda(codigoVenda);
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Produto produtoNovo =
                cadastrarProduto(
                        codigoProduto, "Monitor LG", "Monitor Wide 25", "LG",
                        new BigDecimal("3000.00"));

        Venda vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.adicionarProduto(produtoNovo, quantidadeProdutoNovo);

        service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

        vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.removerProduto(produtoNovo, quantidadeARemover);

        service.removerProdutoDeVenda(vendaCadastrada, produtoNovo, quantidadeARemover);

        vendaCadastrada = service.buscar(codigoVenda);

        Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
        Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
    }

    @Test
    public void removerTodosProdutosDeVendaTeste()
            throws DAOException, RegistroNaoEncontradoException, SQLException {
        Integer quantidadeProdutosEsperada = 0;
        String codigoVenda = "KDSA82";
        String codigoProduto = "DXIE9832";
        Integer quantidadeProdutoNovo = 3;
        BigDecimal valorTotalEsperado = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);

        Venda venda = criarVenda(codigoVenda);
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Produto produtoNovo =
                cadastrarProduto(
                        codigoProduto, "Monitor LG", "Monitor Wide 25", "LG",
                        new BigDecimal("3000.00"));

        Venda vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.adicionarProduto(produtoNovo, quantidadeProdutoNovo);

        service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

        vendaCadastrada = service.buscar(codigoVenda);

        vendaCadastrada.removerTodosItens();

        service.removerTodosProdutosDeVenda(vendaCadastrada);

        vendaCadastrada = service.buscar(codigoVenda);

        Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
        Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
    }

    @Test
    public void concluirVendaTest() throws DAOException, RegistroNaoEncontradoException {
        Venda venda = criarVenda("JS83X");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());

        service.concluirVenda(vendaCadastrada);

        vendaCadastrada = service.buscar(venda.getCodigo());

        Assert.assertEquals(StatusVenda.CONCLUIDA.name(), vendaCadastrada.getStatusVenda().name());
    }

    @Test
    public void adicionarProdutoVendaAposConcluidaTest()
            throws DAOException, RegistroNaoEncontradoException {
        Venda venda = criarVenda("JS83X");
        Boolean cadastrado = service.salvar(venda);
        Assert.assertTrue(cadastrado);

        Venda vendaCadastrada = service.buscar(venda.getCodigo());

        service.concluirVenda(vendaCadastrada);

        Venda vendaConcluida = service.buscar(venda.getCodigo());

        UnsupportedOperationException exception =
                assertThrows(
                        UnsupportedOperationException.class,
                        () -> {
                            vendaConcluida.adicionarProduto(this.produto, 1);
                        });

        Assert.assertEquals("Não é permitido alterar venda concluída", exception.getMessage());
    }

    private Cliente criarCliente() {
        return (Cliente)
                new ClienteFactory()
                        .create(
                                "706.967.703-00",
                                "Laura Cecília Esther Lopes",
                                "(83) 99931-8257",
                                "Rua João José Batista Júnior",
                                "864",
                                "João Pessoa",
                                "PB",
                                StatusCliente.ATIVO);
    }

    private Venda criarVenda(String codigo) {
        Venda venda =
                (Venda)
                        new VendaFactory().create(codigo, Instant.now(), this.cliente, StatusVenda.INICIADA);
        venda.adicionarProduto(this.produto, 1);
        return venda;
    }

    private Cliente cadastrarCliente() throws DAOException {
        Cliente cliente = criarCliente();
        clienteDAO.salvar(cliente);
        return cliente;
    }

    private Produto criarProduto(
            String codigo, String nome, String descricao, String fabricante, BigDecimal valor) {
        return (Produto) new ProdutoFactory().create(codigo, nome, descricao, fabricante, valor);
    }

    private Produto criarProduto() {
        return (Produto)
                new ProdutoFactory()
                        .create("B492", "Notebook Gamer", "Notebook Acer", "ACER",
                                new BigDecimal("6000.00"));
    }

    private Produto cadastrarProduto() throws DAOException {
        Produto produto = criarProduto();
        produtoDAO.salvar(produto);
        return produto;
    }

    private Produto cadastrarProduto(
            String codigo, String nome, String descricao, String fabricante, BigDecimal valor)
            throws DAOException {
        Produto produto = criarProduto(codigo, nome, descricao, fabricante, valor);
        produtoDAO.salvar(produto);
        return produto;
    }

    private void excluirVendas() throws SQLException {
        TestJdbc.executeDelete("DELETE FROM venda");
    }

    private void excluirItemsVenda() throws SQLException {
        TestJdbc.executeDelete("DELETE FROM item_venda");
    }

    private void excluirProdutos() throws DAOException {
        Collection<Produto> produtos = this.produtoDAO.listar();
        for (Produto produto : produtos) {
            this.produtoDAO.excluir(produto.getId());
        }
    }
}
