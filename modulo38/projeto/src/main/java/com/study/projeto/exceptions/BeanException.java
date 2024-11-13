package com.study.projeto.exceptions;

public class BeanException extends Exception {
  public BeanException(String msg) {
    super(msg);
  }
  public BeanException(Exception e, String msg) {
    super(msg, e);
  }
}
