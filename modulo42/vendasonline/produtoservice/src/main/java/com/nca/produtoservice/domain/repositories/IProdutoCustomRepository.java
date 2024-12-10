package com.nca.produtoservice.domain.repositories;

import com.nca.produtoservice.domain.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IProdutoCustomRepository extends MongoRepository<Produto, Long>, CustomRepository {

}
