package com.study.projeto.util;

import java.util.UUID;

public class GeradorCodigo {

  public static String codigo() {
    return UUID.randomUUID().toString();
  }
}
