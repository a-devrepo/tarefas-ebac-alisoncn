package com.study.vendas;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.dao.mocks.ImplClientDAOMock;
import com.study.vendas.domain.Cliente;
import com.study.vendas.services.IClienteService;
import com.study.vendas.services.ImplClienteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClienteServiceTest {

    private IClienteService service;
    private IClienteDAO daoMock;
    Cliente cliente;

    public ClienteServiceTest(){
        daoMock = new ImplClientDAOMock();
        service = new ImplClienteService(daoMock);
    }
    @Before
    public void init(){
        cliente = new Cliente();
        cliente.setCpf("111.111.111-11");
        cliente.setNome("Salomao");
        cliente.setEndereco("Rua A");
        cliente.setNumero("10");
        cliente.setCidade("Rio de Janeiro");
        cliente.setEstado("RJ");
    }

    @Test
    public void salvarclienteTest(){
        Cliente clienteSalvo = service.salvar(cliente);
        Assert.assertNotNull(clienteSalvo);
    }

    @Test
    public void pesquisarClienteTest(){
        Cliente clienteSalvo = service.salvar(cliente);
        Cliente clienteConsultado = service.buscarPorCpf(clienteSalvo.getCpf());
        Assert.assertNotNull(clienteConsultado);
  }

    @Test
    public void excluirClienteTest(){
        Cliente clienteSalvo = service.salvar(cliente);
        service.excluir(clienteSalvo.getCpf());
        Cliente clienteConsultado = service.buscarPorCpf(clienteSalvo.getCpf());
        Assert.assertNull(clienteConsultado);
    }
}
