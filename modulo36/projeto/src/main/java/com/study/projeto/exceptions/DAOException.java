package com.study.projeto.exceptions;

public class DAOException extends Exception {
  public DAOException(String msg) {
    super(msg);
  }

  public DAOException(String msg, Exception e) {
    super(msg, e);
  }
}
