package com.study.vendas.dao;

import com.study.vendas.domain.Cliente;

public interface IClienteDAO {
    Cliente salvar(Cliente cliente);

    Cliente buscarPorCpf(String cpf);
}
