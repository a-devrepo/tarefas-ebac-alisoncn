package com.nca.vendaservice.exceptions;

public class AlteracaoStatusVendaException extends Exception {
  public AlteracaoStatusVendaException(String msg) {
    super(msg);
  }
  public AlteracaoStatusVendaException(Exception e, String msg) {
    super(msg, e);
  }
}
