package com.nca.vendaservice.services;

import com.nca.vendaservice.domain.model.Venda;
import com.nca.vendaservice.domain.repositories.IVendaCustomRepository;
import com.nca.vendaservice.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    private IVendaCustomRepository repository;

    @Autowired
    public VendaService(IVendaCustomRepository repository) {
        this.repository = repository;
    }

    public Venda salvar(Venda venda) throws DAOException {
        return repository.salvar(venda);
    }

}
