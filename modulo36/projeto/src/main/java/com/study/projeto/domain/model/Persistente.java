package com.study.projeto.domain.model;

import com.study.projeto.enums.StatusRegistro;

public interface Persistente {
  StatusRegistro getStatusRegistro();
  void setStatusRegistro(StatusRegistro status);
}
