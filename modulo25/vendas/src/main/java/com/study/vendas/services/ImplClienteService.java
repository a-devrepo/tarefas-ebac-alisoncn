package com.study.vendas.services;

import com.study.vendas.dao.IClienteDAO;
import com.study.vendas.domain.Cliente;
import com.study.vendas.exceptions.RegistroNaoEncontradoException;
import com.study.vendas.services.generic.ImplGenericService;

public class ImplClienteService extends ImplGenericService<Cliente,Long> implements IClienteService {

    public ImplClienteService(IClienteDAO dao){
        super(dao);
    }

    public Cliente buscarPorCpf(String cpf) throws RegistroNaoEncontradoException {
        return listar().stream()
                    .filter(cliente -> cliente.getCpf().equals(cpf)).findFirst()
                    .orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));
    }

    public void excluirPorCpf(String cpf) throws RegistroNaoEncontradoException {
        Cliente cliente = buscarPorCpf(cpf);
        excluir(cliente.getCodigo());
    }


    private void alterarDados(Cliente cliente, Cliente clienteOld) {
        clienteOld.setNome(cliente.getNome());
        clienteOld.setTelefone(cliente.getTelefone());
        clienteOld.setEndereco(cliente.getEndereco());
        clienteOld.setNumero(cliente.getNumero());
        clienteOld.setCidade(cliente.getCidade());
        clienteOld.setEstado(cliente.getEstado());
    }
}
