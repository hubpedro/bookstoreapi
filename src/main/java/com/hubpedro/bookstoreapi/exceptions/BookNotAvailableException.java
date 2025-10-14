package com.hubpedro.bookstoreapi.exceptions;

public class BookNotAvailableException extends RuntimeException {
  public BookNotAvailableException(String message) {
    super(message);
  }
}
