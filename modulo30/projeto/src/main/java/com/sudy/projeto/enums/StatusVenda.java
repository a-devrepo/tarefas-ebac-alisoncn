package com.sudy.projeto.enums;

import java.util.Objects;

public enum StatusVenda {
  INICIADA,
  CONCLUIDA,
  CANCELADA;

  public static StatusVenda getByName(String value) {
    for (StatusVenda status : StatusVenda.values()) {
      if (status.name().equals(value.toUpperCase())) {
        return status;
      }
    }
    return null;
  }

  public static StatusVenda getByname(String name) {
    StatusVenda status = null;
    for (StatusVenda statusVenda : StatusVenda.values()) {
      if (statusVenda.name().equals(name.toUpperCase())) {
        status = statusVenda;
      }
    }
    if (Objects.isNull(status)) {
      throw new RuntimeException("Status de venda não encontrado:");
    }
    return status;
  }
}
