package br.com.estudojpa.domain.dao;

import static org.junit.Assert.*;

import br.com.estudojpa.domain.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IProdutoDAOTest {
  private IProdutoDAO dao;

  @Before
  public void setup() {
    dao = new ImplProdutoDAO();
  }

  @After
  public void end() {
    List<Produto> lista = dao.listar();
    for (Produto p : lista) {
      dao.remover(p);
    }
  }

  @Test
  public void cadastrarClienteTest() {
    Produto produto = new Produto("A4RUCG", "Notebook", "Dell", new BigDecimal("6000"));
    produto = dao.salvar(produto);

    assertNotNull(produto.getId());
  }

  @Test
  public void listarProdutosTest() {
    Produto produto1 = new Produto("A4RUCG", "Notebook", "Dell", new BigDecimal("6000"));
    Produto produto2 = new Produto("JS89BS", "Cadeira", "DXRacer", new BigDecimal("2000"));
    produto1 = dao.salvar(produto1);
    produto2 = dao.salvar(produto2);

    List<Produto> lista = dao.listar();
    assertEquals(2, lista.size());
  }

  @Test
  public void atualizarProdutoTest() {

    String nome = "Notebook Gamer";
    String marca = "AlienWare";
    BigDecimal preco = new BigDecimal("10000.00");

    Produto produto1 = new Produto("A4RUCG", "Notebook", "Dell", new BigDecimal("6000"));
    produto1 = dao.salvar(produto1);
    produto1.setNome(nome);
    produto1.setMarca(marca);
    produto1.setPreco(preco);

    dao.atualizar(produto1);

    Produto produtoAtualizado = dao.buscar(produto1.getCodigo()).get(0);
    assertEquals(nome, produtoAtualizado.getNome());
    assertEquals(marca, produtoAtualizado.getMarca());
    assertEquals(preco, produtoAtualizado.getPreco());
  }

  @Test
  public void removerProdutoTest() {
    Produto produto1 = new Produto("A4RUCG", "Notebook", "Dell", new BigDecimal("6000"));
    produto1 = dao.salvar(produto1);

    dao.remover(produto1);
    List<Produto> list = dao.buscar(produto1.getCodigo());
    Assert.assertEquals(0,list.size());
  }
}
