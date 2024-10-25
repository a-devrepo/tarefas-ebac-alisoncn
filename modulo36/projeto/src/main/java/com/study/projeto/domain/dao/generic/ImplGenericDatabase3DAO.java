package com.study.projeto.domain.dao.generic;

import com.study.projeto.domain.dao.util.Constants;
import com.study.projeto.domain.model.Persistente;

import java.io.Serializable;

public abstract class ImplGenericDatabase3DAO<T extends Persistente, E extends Serializable>
    extends ImplGenericDAO<T,E> {
  public ImplGenericDatabase3DAO(Class<T> persistenteClass) {
    super(persistenteClass, Constants.Databases.PERSISTENCE_UNIT_NAME_DB3);
  }
}
