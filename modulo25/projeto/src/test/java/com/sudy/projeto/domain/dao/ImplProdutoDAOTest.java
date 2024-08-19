package com.sudy.projeto.domain.dao;

import com.sudy.projeto.domain.dao.impl.IProdutoDAO;
import com.sudy.projeto.domain.dao.impl.ImplProdutoDAO;
import com.sudy.projeto.domain.model.Produto;
import com.sudy.projeto.exceptions.CodigoNaoEncontradoException;
import com.sudy.projeto.exceptions.DataBaseException;
import com.sudy.projeto.exceptions.RegistroNaoEncontradoException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImplProdutoDAOTest {

  private IProdutoDAO dao;

  @Before
  public void setup() {
    dao = new ImplProdutoDAO();
  }

  @Test
  public void cadastrarProdutoTest()
      throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Produto produto = new Produto();
    produto.setNome("Notebook");
    produto.setFabricante("Acer");
    produto.setValor(new BigDecimal("7000.00"));

    Long idProduto = (long) dao.salvar(produto);
    
    produto = dao.buscar(idProduto);

    Assert.assertNotNull(produto);

    dao.excluir(produto.getCodigo());

  }

  @Test
  public void listarProdutosTest()
          throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Produto produto1 = new Produto();
    produto1.setNome("Notebook");
    produto1.setFabricante("Acer");
    produto1.setValor(new BigDecimal("7000.00"));

    Produto produto2 = new Produto();
    produto2.setNome("Monitor Gamer");
    produto2.setFabricante("Samsung");
    produto2.setValor(new BigDecimal("3000.00"));

    Long idProduto1 = (long) dao.salvar(produto1);
    Long idProduto2 = (long) dao.salvar(produto2);

    List<Produto> produtos = dao.listar();

    Assert.assertEquals(2,produtos.size());

    dao.excluir(idProduto1);
    dao.excluir(idProduto2);
  }

  @Test
  public void buscarProdutoTest()
          throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Produto produto1 = new Produto();
    produto1.setNome("Notebook");
    produto1.setFabricante("Acer");
    produto1.setValor(new BigDecimal("7000.00"));

    Long idProduto1 = (long) dao.salvar(produto1);

    Produto produto = dao.buscar(idProduto1);

    Assert.assertEquals("Notebook",produto.getNome());
    Assert.assertEquals("Acer",produto.getFabricante());
    Assert.assertEquals(new BigDecimal("7000.00"),produto.getValor());

    dao.excluir(idProduto1);
  }

  @Test
  public void atualizarProdutoTest()
          throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Produto produto1 = new Produto();
    produto1.setNome("Notebook");
    produto1.setFabricante("Acer");
    produto1.setValor(new BigDecimal("7000.00"));

    Long idProduto1 = (long) dao.salvar(produto1);

    Produto produto = dao.buscar(idProduto1);

    produto.setNome("PCGamer");
    produto.setFabricante("Pichau");
    produto.setValor(new BigDecimal("6700.00"));

    dao.atualizar(produto);

    produto = dao.buscar(idProduto1);

    Assert.assertEquals("PCGamer",produto.getNome());
    Assert.assertEquals("Pichau",produto.getFabricante());
    Assert.assertEquals(new BigDecimal("6700.00"),produto.getValor());

    dao.excluir(idProduto1);
  }

  @Test
  public void excluirProdutoTest()
          throws SQLException, CodigoNaoEncontradoException, DataBaseException, IllegalAccessException
          , RegistroNaoEncontradoException {
    Produto produto1 = new Produto();
    produto1.setNome("Notebook");
    produto1.setFabricante("Acer");
    produto1.setValor(new BigDecimal("7000.00"));

    Long idProduto1 = (long) dao.salvar(produto1);

    dao.excluir(idProduto1);

    RegistroNaoEncontradoException exception = Assert
            .assertThrows(RegistroNaoEncontradoException.class, () -> dao.buscar(idProduto1));
    Assert.assertEquals("Registro não encontrado", exception.getMessage());
  }

}
