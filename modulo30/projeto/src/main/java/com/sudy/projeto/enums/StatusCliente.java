package com.sudy.projeto.enums;

import java.util.Objects;

public enum StatusCliente {
  ATIVO,
  INATIVO;

  public static StatusCliente getByName(String value) {
    for (StatusCliente status : StatusCliente.values()) {
      if (status.name().equals(value.toUpperCase())) {
        return status;
      }
    }
    return null;
  }

  public static StatusCliente getByname(String name) {
    StatusCliente status = null;
    for (StatusCliente statusVenda : StatusCliente.values()) {
      if (statusVenda.name().equals(name.toUpperCase())) {
        status = statusVenda;
      }
    }
    if (Objects.isNull(status)) {
      throw new RuntimeException("Status de cliente não encontrado");
    }
    return status;
  }
}
