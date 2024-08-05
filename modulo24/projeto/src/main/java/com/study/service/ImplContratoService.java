package com.study.service;

import com.study.domain.dao.ImplGenericDAO;
import com.study.domain.Contrato;

public class ImplContratoService extends ImplGenericService<Contrato,Long> implements IContratoService {

    public ImplContratoService(ImplGenericDAO<Contrato,Long> dao){
        super(dao);
    }
}
