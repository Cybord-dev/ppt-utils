package com.unknown.error;

public class XMLParserException extends Exception {
  public XMLParserException(String message) {
    super(message);
  }

  public XMLParserException(String message, Throwable t) {
    super(message, t);
  }
}
