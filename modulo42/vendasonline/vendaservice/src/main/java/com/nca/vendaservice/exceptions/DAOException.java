package com.nca.vendaservice.exceptions;

public class DAOException extends Exception {
  public DAOException(String msg) {
    super(msg);
  }

  public DAOException(String msg, Exception e) {
    super(msg, e);
  }
}
