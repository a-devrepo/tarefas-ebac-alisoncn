package com.study.projeto.exceptions;

public class OperacaoNaoPermitidaException extends Exception {
  public OperacaoNaoPermitidaException(String msg) {
    super(msg);
  }
  public OperacaoNaoPermitidaException(String msg,Exception e) {
    super(msg, e);
  }
}
