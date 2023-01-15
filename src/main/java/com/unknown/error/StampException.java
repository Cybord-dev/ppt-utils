package com.unknown.error;

public class StampException extends Exception {

  public StampException(String message) {
    super(message);
  }

  public StampException(String message, Throwable t) {
    super(message, t);
  }
}
