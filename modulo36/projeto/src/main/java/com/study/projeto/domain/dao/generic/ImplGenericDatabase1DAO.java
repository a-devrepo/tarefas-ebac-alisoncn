package com.study.projeto.domain.dao.generic;

import com.study.projeto.domain.dao.util.Constants;
import com.study.projeto.domain.model.Persistente;

import java.io.Serializable;

public abstract class ImplGenericDatabase1DAO<T extends Persistente, E extends Serializable>
    extends ImplGenericDAO<T,E> {
  public ImplGenericDatabase1DAO(Class<T> persistenteClass) {
    super(persistenteClass, Constants.Databases.PERSISTENCE_UNIT_NAME_DB1);
  }
}
