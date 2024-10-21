package com.study.projeto.services.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ClienteBuilder;
import com.study.projeto.domain.builders.EnderecoBuilder;
import com.study.projeto.domain.builders.ProdutoBuilder;
import com.study.projeto.domain.builders.VendaBuilder;
import com.study.projeto.domain.dao.impl.cliente.IClienteDAO;
import com.study.projeto.domain.dao.impl.cliente.ImplClienteDatabase1DAO;
import com.study.projeto.domain.dao.impl.produto.IProdutoDAO;
import com.study.projeto.domain.dao.impl.produto.ImplProdutoDatabase1DAO;
import com.study.projeto.domain.dao.impl.venda.ImplVendaDatabase1DAO;
import com.study.projeto.domain.model.Cliente;
import com.study.projeto.domain.model.Endereco;
import com.study.projeto.domain.model.Produto;
import com.study.projeto.domain.model.Venda;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.enums.StatusVenda;
import com.study.projeto.exceptions.*;
import com.study.projeto.util.GeradorCPF;
import com.study.projeto.util.GeradorCodigo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IVendaServiceTest {

  private IVendaService service;
  private ImplVendaDatabase1DAO dao;
  private IClienteDAO clienteDAO;
  private IProdutoDAO produtoDAO;
  private Cliente cliente;
  private Produto produto;

  public IVendaServiceTest() {
    dao = new ImplVendaDatabase1DAO();
    service = new ImplVendaService(dao);
    clienteDAO = new ImplClienteDatabase1DAO();
    produtoDAO = new ImplProdutoDatabase1DAO();
  }

  @Before
  public void init() throws DAOException{
    this.cliente = cadastrarCliente();
    this.produto = cadastrarProduto();
  }

  @After
  public void end() throws DAOException {
    excluirVendas();
    excluirProdutos();
    clienteDAO.excluir(this.cliente);
  }

  @Test
  public void salvarVendaTest() throws DAOException, OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    Venda cadastrado = service.salvar(venda);
    Assert.assertNotNull(cadastrado);
  }

  @Test
  public void salvarVendaComCodigoDuplicadoTest()
      throws DAOException, OperacaoNaoPermitidaException{
    String codigoVenda = GeradorCodigo.codigo();
    Venda venda = criarVenda(codigoVenda);
    Venda cadastrado = service.salvar(venda);
    Assert.assertNotNull(cadastrado);
    String mensagemErroEsperada = "Já existe registro com o código: " + codigoVenda;

    Venda novaVenda = criarVenda(codigoVenda);

    DAOException daoException = assertThrows(DAOException.class, () -> service.salvar(novaVenda));
    Assert.assertEquals(mensagemErroEsperada, daoException.getMessage());
  }

  @Test
  public void consultarVendaTest()
      throws DAOException, RegistroNaoEncontradoException, OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    Venda cadastrado = service.salvar(venda);
    Assert.assertNotNull(cadastrado);

    Venda vendaCadastrada = service.buscar(venda.getId());
    Assert.assertEquals(new BigDecimal("6000.00"), vendaCadastrada.getValorTotal());
    Assert.assertEquals(StatusVenda.INICIADA, vendaCadastrada.getStatusVenda());
  }

  @Test
  public void cancelarVendaTest()
      throws DAOException,
          RegistroNaoEncontradoException,
          AlteracaoStatusVendaException,
          OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Venda vendaCadastrada = service.buscar(venda.getId());

    service.cancelarVenda(vendaCadastrada);

    vendaCadastrada = service.buscar(venda.getId());

    Assert.assertEquals(StatusVenda.CANCELADA, vendaCadastrada.getStatusVenda());
  }

  @Test
  public void finalizarVendaTest()
      throws DAOException,
          RegistroNaoEncontradoException,
          AlteracaoStatusVendaException,
          OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    Venda cadastrado = service.salvar(venda);
    Assert.assertNotNull(cadastrado);

    Venda vendaCadastrada = service.buscar(venda.getId());

    service.concluirVenda(vendaCadastrada);

    vendaCadastrada = service.buscar(venda.getId());

    Assert.assertEquals(StatusVenda.CONCLUIDA.name(), vendaCadastrada.getStatusVenda().name());
  }

  @Test
  public void aumentarQuantidadedeProdutoDaVenda()
      throws DAOException, RegistroNaoEncontradoException, OperacaoNaoPermitidaException{
    Integer quantidadeProdutosEsperada = 2;
    Integer quantidadeAdicional = 1;
    Venda venda = criarVenda(GeradorCodigo.codigo());
    BigDecimal valorTotalEsperado = new BigDecimal("12000.00");
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Venda vendaCadastrada = service.buscarVendaItens(venda);

    service.alterarQuantidadeProdutosItemVenda(
        vendaCadastrada, produto.getCodigo(), quantidadeAdicional);

    vendaCadastrada = service.buscarVendaItens(venda);

    Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
    Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
  }

  @Test
  public void cadastrarProdutoDiferenteAVendaTeste()
      throws DAOException, RegistroNaoEncontradoException, OperacaoNaoPermitidaException{
    Integer quantidadeProdutosEsperada = 2;
    String codigoVenda = GeradorCodigo.codigo();
    String codigoProduto = GeradorCodigo.codigo();
    Integer quantidadeProdutoNovo = 1;
    BigDecimal valorTotalEsperado = new BigDecimal("9000.00");

    Venda venda = criarVenda(codigoVenda);
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Produto produtoDiferente =
        cadastrarProduto(
            codigoProduto, "Monitor LG", "Monitor Wide 25", "LG", new BigDecimal("3000.00"));

    Venda vendaCadastrada = service.buscarVendaItens(venda);

    service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoDiferente, quantidadeProdutoNovo);

    vendaCadastrada = service.buscarVendaItens(venda);

    Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
    Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
  }

  @Test
  public void removerProdutoVendaTeste()
      throws DAOException,
          RegistroNaoEncontradoException,
          OperacaoNaoPermitidaException{
    Integer quantidadeProdutosEsperada = 1;
    String codigoVenda = GeradorCodigo.codigo();
    String codigoProduto = GeradorCodigo.codigo();
    Integer quantidadeProdutoNovo = 1;
    BigDecimal valorTotalEsperado = new BigDecimal("3000.00");
    Integer quantidadeARemover = 1;

    Venda venda = criarVenda(codigoVenda);
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Produto produtoNovo =
        cadastrarProduto(
            codigoProduto, "Monitor LG", "Monitor Wide 25", "LG", new BigDecimal("3000.00"));

    Venda vendaCadastrada = service.buscarVendaItens(venda);

    service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

    vendaCadastrada = service.buscarVendaItens(venda);


    service.removerProdutoDeVenda(vendaCadastrada, this.produto, quantidadeARemover);

    vendaCadastrada = service.buscarVendaItens(venda);

    Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
    Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
  }

  @Test
  public void removerUmaUnidadeDeProdutoVendaTeste()
      throws DAOException,
          RegistroNaoEncontradoException,
          OperacaoNaoPermitidaException{
    Integer quantidadeProdutosEsperada = 3;
    String codigoVenda = GeradorCodigo.codigo();
    String codigoProduto = GeradorCodigo.codigo();
    Integer quantidadeProdutoNovo = 3;
    BigDecimal valorTotalEsperado = new BigDecimal("12000.00");
    Integer quantidadeARemover = 1;

    Venda venda = criarVenda(codigoVenda);
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Produto produtoNovo =
        cadastrarProduto(
            codigoProduto, "Monitor LG", "Monitor Wide 25", "LG", new BigDecimal("3000.00"));

    Venda vendaCadastrada = service.buscarVendaItens(venda);

    service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

    vendaCadastrada = service.buscarVendaItens(venda);

    service.removerProdutoDeVenda(vendaCadastrada, produtoNovo, quantidadeARemover);

    vendaCadastrada = service.buscarVendaItens(venda);

    Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
    Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
  }

  @Test
  public void removerTodosProdutosDeVendaTeste()
      throws DAOException,
          RegistroNaoEncontradoException,
          OperacaoNaoPermitidaException{
    Integer quantidadeProdutosEsperada = 0;
    String codigoVenda = GeradorCodigo.codigo();
    String codigoProduto = GeradorCodigo.codigo();
    Integer quantidadeProdutoNovo = 3;
    BigDecimal valorTotalEsperado = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);

    Venda venda = criarVenda(codigoVenda);
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Produto produtoNovo =
        cadastrarProduto(
            codigoProduto, "Monitor LG", "Monitor Wide 25", "LG", new BigDecimal("3000.00"));

    Venda vendaCadastrada = service.buscarVendaItens(venda);

    service.adicionarNovoProdutoAVenda(vendaCadastrada, produtoNovo, quantidadeProdutoNovo);

    vendaCadastrada = service.buscarVendaItens(venda);

    service.removerTodosProdutosDeVenda(vendaCadastrada);

    vendaCadastrada = service.buscarVendaItens(vendaCadastrada);

    Assert.assertEquals(quantidadeProdutosEsperada, vendaCadastrada.getQuantidadeTotalProdutos());
    Assert.assertEquals(valorTotalEsperado, vendaCadastrada.getValorTotal());
  }

  @Test
  public void concluirVendaTest()
      throws DAOException,
          RegistroNaoEncontradoException,
          AlteracaoStatusVendaException,
          OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    Venda cadastrado = service.salvar(venda);
    Assert.assertNotNull(cadastrado);

    Venda vendaCadastrada = service.buscar(venda.getId());

    service.concluirVenda(vendaCadastrada);

    vendaCadastrada = service.buscar(venda.getId());

    Assert.assertEquals(StatusVenda.CONCLUIDA.name(), vendaCadastrada.getStatusVenda().name());
  }

  @Test
  public void adicionarProdutoVendaAposConcluidaTest()
      throws DAOException,
          RegistroNaoEncontradoException,
          AlteracaoStatusVendaException,
          OperacaoNaoPermitidaException{
    Venda venda = criarVenda(GeradorCodigo.codigo());
    venda = service.salvar(venda);
    Assert.assertNotNull(venda);

    Venda vendaCadastrada = service.buscar(venda.getId());

    service.concluirVenda(vendaCadastrada);

    Venda vendaConcluida = service.buscarVendaItens(venda);

    OperacaoNaoPermitidaException exception =
        assertThrows(
                OperacaoNaoPermitidaException.class,
            () -> {
              vendaConcluida.adicionarProduto(this.produto, 1);
            });

    Assert.assertEquals("Não é permitido adicionar itens à venda concluída", exception.getMessage());
  }

  private Cliente criarCliente() {
    Endereco endereco = criarEndereco();
    return ClienteBuilder.builder()
        .cpf(GeradorCPF.cpf(true))
        .nome("Laura Cecília Esther Lopes")
        .telefone("(83) 99931-8257")
        .endereco(endereco)
        .status(StatusCliente.ATIVO)
        .build();
  }

  private Endereco criarEndereco() {
    Endereco endereco =
        EnderecoBuilder.builder()
            .logradouro("Rua João José Batista Júnior")
            .numero("864")
            .cidade("João Pessoa")
            .estado("PB")
            .build();
    return endereco;
  }

  private Venda criarVenda(String codigo) throws OperacaoNaoPermitidaException {
    Venda venda =
        VendaBuilder.builder()
            .codigo(codigo)
            .dataVenda(Instant.now())
            .cliente(this.cliente)
            .build();
    venda.adicionarProduto(this.produto, 1);
    return venda;
  }

  private Cliente cadastrarCliente() throws DAOException{
    Cliente cliente = criarCliente();
    clienteDAO.salvar(cliente);
    return cliente;
  }

  private Produto criarProduto(
      String codigo, String nome, String descricao, String fabricante, BigDecimal valor) {
    return ProdutoBuilder.builder()
        .codigo(codigo)
        .nome(nome)
        .descricao(descricao)
        .fabricante(fabricante)
        .valor(valor)
        .build();
  }

  private Produto criarProduto() {
    return ProdutoBuilder.builder()
        .codigo(GeradorCodigo.codigo())
        .nome("Notebook Gamer")
        .descricao("Notebook Acer")
        .fabricante("ACER")
        .valor(new BigDecimal("6000.00"))
        .build();
  }

  private Produto cadastrarProduto() throws DAOException{
    Produto produto = criarProduto();
    produtoDAO.salvar(produto);
    return produto;
  }

  private Produto cadastrarProduto(
      String codigo, String nome, String descricao, String fabricante, BigDecimal valor)
      throws DAOException{
    Produto produto = criarProduto(codigo, nome, descricao, fabricante, valor);
    produtoDAO.salvar(produto);
    return produto;
  }

  private void excluirVendas() throws DAOException {
    Collection<Venda> list = service.listar();
    for (Venda venda : list) {
      service.excluir(venda);
    }
  }

  private void excluirProdutos() throws DAOException {
    Collection<Produto> produtos = this.produtoDAO.listar();
    for (Produto produto : produtos) {
      this.produtoDAO.excluir(produto);
    }
  }
}
