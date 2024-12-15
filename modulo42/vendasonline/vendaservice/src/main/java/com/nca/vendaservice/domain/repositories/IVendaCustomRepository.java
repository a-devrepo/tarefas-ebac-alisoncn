package com.nca.vendaservice.domain.repositories;

import com.nca.vendaservice.domain.model.Venda;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IVendaCustomRepository extends MongoRepository<Venda, String>, CustomRepository {

}
