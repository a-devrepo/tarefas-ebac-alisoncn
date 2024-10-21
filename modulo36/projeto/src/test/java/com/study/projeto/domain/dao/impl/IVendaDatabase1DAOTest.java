package com.study.projeto.domain.dao.impl;

import static org.junit.Assert.*;

import com.study.projeto.domain.builders.ClienteBuilder;
import com.study.projeto.domain.builders.EnderecoBuilder;
import com.study.projeto.domain.builders.ProdutoBuilder;
import com.study.projeto.domain.builders.VendaBuilder;
import com.study.projeto.domain.dao.impl.cliente.IClienteDAO;
import com.study.projeto.domain.dao.impl.cliente.ImplClienteDatabase1DAO;
import com.study.projeto.domain.dao.impl.produto.IProdutoDAO;
import com.study.projeto.domain.dao.impl.produto.ImplProdutoDatabase1DAO;
import com.study.projeto.domain.dao.impl.venda.IVendaDAO;
import com.study.projeto.domain.dao.impl.venda.ImplVendaDatabase1DAO;
import com.study.projeto.domain.model.Cliente;
import com.study.projeto.domain.model.Endereco;
import com.study.projeto.domain.model.Produto;
import com.study.projeto.domain.model.Venda;
import com.study.projeto.enums.StatusCliente;
import com.study.projeto.enums.StatusVenda;
import com.study.projeto.exceptions.DAOException;
import com.study.projeto.exceptions.OperacaoNaoPermitidaException;
import com.study.projeto.util.GeradorCPF;
import com.study.projeto.util.GeradorCodigo;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IVendaDatabase1DAOTest {

  private IVendaDAO vendaDAO;
  private IClienteDAO clienteDAO;
  private IProdutoDAO produtoDAO;
  private Cliente cliente;
  private Produto produto;

  public IVendaDatabase1DAOTest() {
    vendaDAO = new ImplVendaDatabase1DAO();
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
    String codigo = GeradorCodigo.codigo();
    Venda venda = criarVenda(codigo);
    Venda cadastrada = vendaDAO.salvar(venda);
    Assert.assertNotNull(cadastrada);
  }

  @Test
  public void salvarVendaComCodigoDuplicadoTest()
      throws DAOException, OperacaoNaoPermitidaException{
    String codigoVenda = GeradorCodigo.codigo();
    Venda venda = criarVenda(codigoVenda);
    Venda cadastrada = vendaDAO.salvar(venda);
    Assert.assertNotNull(cadastrada);
    String mensagemErroEsperada = "Já existe registro com o código: " + codigoVenda;

    Venda novaVenda = criarVenda(codigoVenda);

    DAOException daoException = assertThrows(DAOException.class, () -> vendaDAO.salvar(novaVenda));
    Assert.assertEquals(mensagemErroEsperada, daoException.getMessage());
  }

  @Test
  public void consultarVendaTest() throws DAOException, OperacaoNaoPermitidaException{
    String codigoVenda = GeradorCodigo.codigo();;
    Venda venda = criarVenda(codigoVenda);
    Venda cadastrada = vendaDAO.salvar(venda);
    Assert.assertNotNull(cadastrada);

    Assert.assertEquals(new BigDecimal("6000.00"), cadastrada.getValorTotal());
    Assert.assertEquals(StatusVenda.INICIADA, cadastrada.getStatusVenda());
  }

  private Cliente criarCliente() {
    String cpf = GeradorCPF.cpf(true);
    Endereco endereco = criarEndereco();
    return ClienteBuilder.builder()
        .cpf(cpf)
        .nome("Laura Cecília Esther Lopes")
        .telefone("(83) 99931-8257")
        .endereco(endereco)
        .status(StatusCliente.ATIVO)
        .build();
  }

  public Endereco criarEndereco() {
    return EnderecoBuilder.builder()
        .logradouro("Rua João José Batista Júnior")
        .numero("864")
        .cidade("João Pessoa")
        .estado("PB")
        .build();
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
    String codigoProduto = GeradorCodigo.codigo();;
    return ProdutoBuilder.builder()
        .codigo(codigoProduto)
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
    for (Venda venda : vendaDAO.listar()) {
      vendaDAO.excluir(venda);
    }
  }

  private void excluirProdutos() throws DAOException {
    Collection<Produto> produtos = this.produtoDAO.listar();
    for (Produto produto : produtos) {
      this.produtoDAO.excluir(produto);
    }
  }
}
