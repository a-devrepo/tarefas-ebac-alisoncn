package com.nca.vendaservice.services;

import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.domain.repositories.IVendaCustomRepository;
import com.nca.vendaservice.exceptions.DAOException;
import com.nca.vendaservice.exceptions.OperacaoNaoPermitidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class VendaService {

    private IVendaCustomRepository repository;

    @Autowired
    public VendaService(IVendaCustomRepository repository) {
        this.repository = repository;
    }

    public Venda salvar(Venda venda) throws DAOException, OperacaoNaoPermitidaException {
        venda.setDataVenda(Instant.now());
        return repository.salvar(venda);
    }

    public Page<Venda> listar(Pageable pageable) throws DAOException {
        return repository.listar(pageable);
    }
}
