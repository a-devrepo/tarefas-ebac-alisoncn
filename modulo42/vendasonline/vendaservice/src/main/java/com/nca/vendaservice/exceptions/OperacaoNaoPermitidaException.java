package com.nca.vendaservice.exceptions;

import java.io.Serial;

public class OperacaoNaoPermitidaException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 8174042090044733359L;

  public OperacaoNaoPermitidaException(String msg) {
    super(msg);
  }
  public OperacaoNaoPermitidaException(String msg,Exception e) {
    super(msg, e);
  }
}
