package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.model.Acessorio;
import br.com.study.carros.domain.model.Marca;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IAcessorioDaoTest {

  IAcessorioDao dao;
  IMarcaDao marcaDao;

  @Before
  public void setup() {
    this.dao = new ImplAcessorioDao();
    this.marcaDao = new ImplMarcaDao();
  }

  @After
  public void end() {
    for (Acessorio acessorio : dao.listar()) {
      dao.remover(acessorio);
    }
  }

  @Test
  public void listarAcessorioTest() {
    Acessorio acessorio1 = criarAcessorio();
    Acessorio acessorio2 =
        new Acessorio("Correia dentada", "Kit de correia dentada"
                , new BigDecimal("500.00"),acessorio1.getMarca());
    dao.salvar(acessorio1);
    dao.salvar(acessorio2);

    List<Acessorio> acessorios = dao.listar();
    Assert.assertEquals(2, acessorios.size());
  }

  @Test
  public void salvarAcessorioTest() {
    Acessorio acessorio = criarAcessorio();
    Acessorio entity = dao.salvar(acessorio);
    Assert.assertNotNull(entity);
    Assert.assertNotNull(entity.getId());
  }

  @Test
  public void buscar() {
    Acessorio acessorio = criarAcessorio();
    acessorio = dao.salvar(acessorio);

    Acessorio entity = dao.buscar(acessorio.getId());
    Assert.assertEquals(acessorio.getId(), entity.getId());
    Assert.assertEquals(acessorio.getNome(), entity.getNome());
    Assert.assertEquals(acessorio.getDescricao(), entity.getDescricao());
    Assert.assertEquals(acessorio.getPreco(), entity.getPreco());
  }

  @Test
  public void remover() {
    Acessorio acessorio = criarAcessorio();
    acessorio = dao.salvar(acessorio);

    dao.remover(acessorio);
    Acessorio entity = dao.buscar(acessorio.getId());
    Assert.assertNull(entity);
  }

  private Acessorio criarAcessorio() {
    Marca marca = criarMarca();
    return new Acessorio(
        "Pastilha de Freio", "Par de pastilhas de freio", new BigDecimal("200.00"), marca);
  }

  private Marca criarMarca() {
    Marca marca = new Marca("Motorcraft", "U.S.A");
    marca = marcaDao.salvar(marca);
    return marca;
  }
}
