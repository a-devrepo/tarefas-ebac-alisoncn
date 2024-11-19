package com.nca.project.domain.repositories;

import com.nca.project.domain.model.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteRepository extends CrudRepository<Cliente, Long> {
}
