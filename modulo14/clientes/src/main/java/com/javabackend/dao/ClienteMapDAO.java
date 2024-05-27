package com.javabackend.dao;

import com.javabackend.domain.Cliente;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alison
 */
public class ClienteMapDAO implements IClienteDao {

    private Map<String, Cliente> map;

    public ClienteMapDAO() {
        this.map = new HashMap<>();
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        if (this.map.containsKey(cliente.getCpf())) {
            return false;
        }
        this.map.put(cliente.getCpf(), cliente);
        return true;
    }

    @Override
    public void excluir(String cpf) {
        Cliente clienteCadastrado = this.map.get(cpf);
        if (clienteCadastrado != null) {
            this.map.remove(cpf, clienteCadastrado);
        }
    }

    @Override
    public void alterar(Cliente cliente) {
        Cliente clienteCadastrado = this.map.get(cliente.getCpf());
        if (clienteCadastrado != null) {
            cliente.setNome(cliente.getNome());
            cliente.setTelefone(cliente.getTelefone());
            cliente.setEndereco(cliente.getEndereco());
            cliente.setNumero(cliente.getNumero());
            cliente.setCidade(cliente.getCidade());
            cliente.setEstado(cliente.getEstado());
        }
    }

    @Override
    public Cliente consultar(String cpf) {
        return this.map.get(cpf);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return this.map.values();
    }
}
