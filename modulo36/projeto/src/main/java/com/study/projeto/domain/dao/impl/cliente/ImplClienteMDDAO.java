package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.ImplMDGenericDAO;
import com.study.projeto.domain.model.nonrelational.ClienteMD;
import com.study.projeto.exceptions.DAOException;
import dev.morphia.query.experimental.updates.UpdateOperators;
import org.bson.types.ObjectId;

public class ImplClienteMDDAO extends ImplMDGenericDAO<ClienteMD, ObjectId>
    implements IClienteMDDAO {
  public ImplClienteMDDAO() {
    super(ClienteMD.class);
  }

  @Override
  public ClienteMD atualizar(ClienteMD entity) throws DAOException {
    datastore
        .find(ClienteMD.class)
        .modify(
            UpdateOperators.set("nome", entity.getNome()),
            UpdateOperators.set("telefone", entity.getTelefone()))
        .execute();

    return entity;
  }
}
