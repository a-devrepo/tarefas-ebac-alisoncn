package com.nca.clienteservice.services;

import com.nca.clienteservice.domain.model.Cliente;
import com.nca.clienteservice.domain.repositories.IClienteCustomRepository;
import com.nca.clienteservice.exceptions.DAOException;
import com.nca.clienteservice.exceptions.RegistroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ClienteService {

    private IClienteCustomRepository repository;

    @Autowired
    public ClienteService(IClienteCustomRepository repository) {
        this.repository = repository;
    }

    public Page<Cliente> listar(Pageable pageable) throws DAOException {
        return this.repository.listar(pageable);
    }

    public Cliente buscar(Long id) throws DAOException, RegistroNaoEncontradoException {
        return this.repository.buscar(id);
    }

    public Cliente buscarPorCpf(String cpf) throws DAOException, RegistroNaoEncontradoException {
        Optional<Cliente> cliente = this.repository.findByCpf(cpf);
        if (!cliente.isPresent()) {
            throw new RegistroNaoEncontradoException("Registro nao encontrado");
        }
        return cliente.get();
    }

    public Cliente cadastrar(Cliente cliente) throws DAOException {
        return this.repository.salvar(cliente);
    }

    public Cliente atualizar(Cliente cliente) throws DAOException {
        return this.repository.atualizar(cliente);
    }

    public void remover(Long id) throws DAOException {
        this.repository.excluir(id);
    }

    public Boolean isCadastrado(Long id){
        Optional<Cliente> cliente = this.repository.findById(id);
        return cliente.isPresent();
    }
}
