package com.nca.clienteservice.domain.repositories;

import com.nca.clienteservice.domain.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteCustomRepository extends MongoRepository<Cliente, Long>, CustomRepository {
    Optional<Cliente> findByCpf(String email);
}
