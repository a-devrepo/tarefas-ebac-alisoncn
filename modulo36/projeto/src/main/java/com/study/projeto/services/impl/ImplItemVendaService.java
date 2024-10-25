package com.study.projeto.services.impl;

import com.study.projeto.domain.dao.generic.IGenericDAO;
import com.study.projeto.domain.model.relational.ItemVenda;
import com.study.projeto.services.generic.ImplGenericService;

public class ImplItemVendaService extends ImplGenericService<ItemVenda, Long>
    implements IItemVendaService {
  public ImplItemVendaService(IGenericDAO dao) {
    super(dao);
  }
}
