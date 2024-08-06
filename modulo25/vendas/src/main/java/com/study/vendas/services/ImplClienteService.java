package com.study.vendas.services;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.domain.Cliente;

public class ImplClienteService implements IClienteService {

    private IClienteDAO dao;

    public ImplClienteService(IClienteDAO dao){
        this.dao = dao;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        cliente = dao.salvar(cliente);
        return cliente;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return dao.buscarPorCpf(cpf);
    }
}
