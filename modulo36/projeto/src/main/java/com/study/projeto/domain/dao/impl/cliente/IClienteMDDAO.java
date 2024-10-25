package com.study.projeto.domain.dao.impl.cliente;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.nonrelational.ClienteMD;
import com.study.projeto.domain.model.relational.Cliente;
import org.bson.types.ObjectId;

public interface IClienteMDDAO extends IGenericDAO<ClienteMD, ObjectId> {}
