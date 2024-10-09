package br.com.study.carros.domain.dao;

import br.com.study.carros.domain.enums.TipoCombustivel;
import br.com.study.carros.domain.model.Acessorio;
import br.com.study.carros.domain.model.Carro;
import br.com.study.carros.domain.model.Marca;
import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ICarroDaoTest {

  IAcessorioDao acessorioDao;
  IMarcaDao marcaDao;
  ICarroDao carroDao;

  @Before
  public void setup() {
    this.acessorioDao = new ImplAcessorioDao();
    this.marcaDao = new ImplMarcaDao();
    this.carroDao = new ImplCarroDao();
  }

  @After
  public void end() {
    for (Carro carro : carroDao.listar()) {
      carroDao.remover(carro);
    }
  }

  @Test
  public void listarCarrosTest() {
    Marca marca1 = criarMarca("Renault", "França");
    Marca marca2 = criarMarca("BYD", "China");
    Carro carro1 = criarCarro("Kardian", Year.of(2024)
            , false, 4, TipoCombustivel.FLEX, new BigDecimal("100000.00"),marca1);
    Carro carro2 = criarCarro("Shark", Year.of(2024)
            , false, 4, TipoCombustivel.FLEX, new BigDecimal("100000.00"),marca2);
    carroDao.salvar(carro1);
    carroDao.salvar(carro2);

    List<Carro> carros = carroDao.listar();
    Assert.assertEquals(2, carros.size());
  }

  @Test
  public void salvarCarroTest() {
    Marca marca1 = criarMarca("Renault", "França");
    Carro carro1 = criarCarro("Kardian", Year.of(2024)
            , false, 4, TipoCombustivel.FLEX, new BigDecimal("100000.00"),marca1);
    Carro entity = carroDao.salvar(carro1);
    Assert.assertNotNull(entity);
    Assert.assertNotNull(entity.getId());
  }

  @Test
  public void buscarCarroTest() {
    Marca marca1 = criarMarca("Renault", "França");
    Carro carro1 = criarCarro("Kardian", Year.of(2024)
            , false, 4, TipoCombustivel.FLEX, new BigDecimal("100000.00"),marca1);
    carro1 = carroDao.salvar(carro1);

    Carro entity = carroDao.buscar(carro1.getId());
    Assert.assertEquals(carro1.getNome(), entity.getNome());
    Assert.assertEquals(carro1.getAno(), entity.getAno());
    Assert.assertEquals(carro1.isAutomatico(), entity.isAutomatico());
    Assert.assertEquals(carro1.getTipoCombustivel(), entity.getTipoCombustivel());
    Assert.assertEquals(carro1.getPreco(), entity.getPreco());
    Assert.assertEquals(carro1.getMarca(), entity.getMarca());
  }

  @Test
  public void removerCarroTest() {

    Marca marca1 = criarMarca("Renault", "França");
    Carro carro1 = criarCarro("Kardian", Year.of(2024)
            , false, 4, TipoCombustivel.FLEX, new BigDecimal("100000.00"),marca1);
    carro1 = carroDao.salvar(carro1);
    carroDao.remover(carro1);
    Carro entity = carroDao.buscar(carro1.getId());
    Assert.assertNull(entity);
  }



  private Carro criarCarro(
      String nome,
      Year year,
      boolean automatico,
      int numeroPortas,
      TipoCombustivel combustivel,
      BigDecimal preco,
      Marca marca) {
    return new Carro("Sandero", year, automatico, numeroPortas, combustivel, preco, marca);
  }

  private Marca criarMarca(String nome, String pais) {
    Marca marca = new Marca(nome, pais);
    marca = marcaDao.salvar(marca);
    return marca;
  }
}
