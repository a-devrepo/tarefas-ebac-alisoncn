package com.study.dao.service;

import com.study.dao.ImplGenericDAO;
import com.study.dao.domain.Contrato;

public class ImplContratoService extends ImplGenericService<Contrato,Long> implements IContratoService {

    public ImplContratoService(ImplGenericDAO<Contrato,Long> dao){
        super(dao);
    }
}
