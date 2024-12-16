package com.nca.vendaservice.exceptions;

import java.io.Serial;

public class DAOException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 8715431204217692133L;

  public DAOException(String msg) {
    super(msg);
  }

  public DAOException(String msg, Exception e) {
    super(msg, e);
  }
}
