package com.study.vendas;

import com.study.vendas.dao.ImplClientDAOMock;
import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.domain.Cliente;
import com.study.vendas.services.IClienteService;
import com.study.vendas.services.ImplClienteService;
import org.junit.Assert;
import org.junit.Test;

public class ClienteTest {

    private IClienteService service;
    private IClienteDAO daoMock;

    public ClienteTest(){
        daoMock = new ImplClientDAOMock();
        service = new ImplClienteService(daoMock);
    }

    @Test
    public void pesquisarClienteTest(){
        Cliente cliente = new Cliente();
        cliente.setCpf("111.111.111-11");
        cliente.setNome("Salomao");
        cliente.setEndereco("Rua A");
        cliente.setNumero("10");
        cliente.setCidade("Rio de Janeiro");
        cliente.setEstado("RJ");

        cliente = service.salvar(cliente);

        Cliente clienteConsultado = service.buscarPorCpf(cliente.getCpf());

    Assert.assertNotNull(clienteConsultado);
  }
}
