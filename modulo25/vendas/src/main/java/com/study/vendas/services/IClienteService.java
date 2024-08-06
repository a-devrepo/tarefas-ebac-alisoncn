package com.study.vendas.services;

import com.study.vendas.domain.Cliente;

public interface IClienteService {
    Cliente salvar(Cliente cliente);

    Cliente buscarPorCpf(String cpf);

    void excluir(String cpf);
}
