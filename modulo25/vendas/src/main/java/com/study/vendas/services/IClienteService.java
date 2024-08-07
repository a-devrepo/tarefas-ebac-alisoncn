package com.study.vendas.services;

import com.study.vendas.domain.Cliente;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import com.study.vendas.services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {
    public Cliente buscarPorCpf(String cpf) throws RegistroNaoEncontradoException;
    public void excluirPorCpf(String cpf) throws RegistroNaoEncontradoException;
}
