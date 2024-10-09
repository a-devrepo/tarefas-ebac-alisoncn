package br.com.study.carros.domain.dao;

import static org.junit.Assert.*;

import br.com.study.carros.domain.model.Marca;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IMarcaDaoTest {

  IMarcaDao dao;

  @Before
  public void setup() {
    this.dao = new ImplMarcaDao();
  }

  @After
  public void end() {
    for(Marca marca: dao.listar()){
      dao.remover(marca);
    }
  }

  @Test
  public void listarMarcaTest() {
    Marca marca1 = criarMarca();
    Marca marca2 = new Marca("Pirelli","Brasil");
    dao.salvar(marca1);
    dao.salvar(marca2);

    List<Marca> marcas = dao.listar();
    Assert.assertEquals(2, marcas.size());
  }

  @Test
  public void salvarMarcaTest() {
    Marca marca = criarMarca();
    Marca entity = dao.salvar(marca);
    Assert.assertNotNull(entity);
    Assert.assertNotNull(entity.getId());
  }

  @Test
  public void buscar() {
    Marca marca = criarMarca();
    marca = dao.salvar(marca);

    Marca entity = dao.buscar(marca.getId());
    Assert.assertEquals(marca.getId(),entity.getId());
    Assert.assertEquals(marca.getNome(),entity.getNome());
    Assert.assertEquals(marca.getPaisOrigem(),entity.getPaisOrigem());
  }

  @Test
  public void remover() {
    Marca marca = criarMarca();
    marca = dao.salvar(marca);

    dao.remover(marca);
    Marca entity = dao.buscar(marca.getId());
    Assert.assertNull(entity);
  }

  private Marca criarMarca() {
    return new Marca("Motorcraft", "U.S.A");
  }
}
