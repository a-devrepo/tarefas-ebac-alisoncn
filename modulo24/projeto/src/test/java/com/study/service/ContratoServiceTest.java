package com.study.service;

import com.study.domain.dao.ImplContratoDAO;
import com.study.domain.dao.ImplGenericDAO;
import com.study.domain.Contrato;
import org.junit.Assert;
import org.junit.Test;

public class ContratoServiceTest {

  @Test
  public void salvarContratoServiceTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato contrato = service.cadastrar(new Contrato());
    Assert.assertNotNull(contrato);
  }

  @Test
  public void salvarContratoNuloDeveLancarExceptionTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> service.cadastrar(null));
    Assert.assertEquals("Objeto não pode ser nulo",exception.getMessage());
  }

  @Test
  public void buscarContratoDeveRetornarObjetoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato esperado = service.cadastrar(new Contrato());
    Contrato atual = service.buscar(esperado.getCodigo());
    Assert.assertNotNull(atual);
    Assert.assertEquals(esperado.getCodigo(),atual.getCodigo());
  }

  @Test
  public void buscarContratoInexistenteDeveLancarExcecaoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> service.buscar(1324567L));
    Assert.assertEquals("Registro não encontrado",exception.getMessage());
  }

  @Test
  public void atualizarContratoDeveAlterarDadosTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato contrato = new Contrato("Compra notebook");
    contrato = service.cadastrar(contrato);
    contrato.setAssunto("Compra equipamentos");
    service.atualizar(contrato);
    Contrato contratoAtualizado = service.buscar(contrato.getCodigo());
    Assert.assertNotNull(contratoAtualizado);
    Assert.assertEquals(contrato.getCodigo(),contratoAtualizado.getCodigo());
    Assert.assertEquals("Compra equipamentos",contratoAtualizado.getAssunto());
  }

  @Test
  public void atualizarContratoNuloDeveLancarExcecaoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> service.atualizar(null));
    Assert.assertEquals("Objeto não pode ser nulo",exception.getMessage());
  }

  @Test
  public void atualizarContratoInexistenteDeveLancarExcecaoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato contrato = new Contrato();
    contrato.setCodigo(234650L);
    RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> service.atualizar(contrato));
    Assert.assertEquals("Registro não encontrado",exception.getMessage());
  }

  @Test
  public void excluirContratoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato contrato = new Contrato();
    contrato = service.cadastrar(contrato);
    final Long codigo = contrato.getCodigo();
    service.remover(codigo);
    RuntimeException exception =
        Assert.assertThrows(RuntimeException.class, () -> service.remover(codigo));
    Assert.assertEquals("Registro não encontrado",exception.getMessage());
  }

  @Test
  public void excluirContratoInexistenteDeveLancarExcecaoTest() {
    ImplGenericDAO dao = new ImplContratoDAO();
    IContratoService service = new ImplContratoService(dao);
    Contrato contrato = new Contrato();
    contrato.setCodigo(234650L);
    RuntimeException exception = Assert.assertThrows(RuntimeException.class,
            () -> service.remover(contrato.getCodigo()));
    Assert.assertEquals("Registro não encontrado",exception.getMessage());
  }
}
