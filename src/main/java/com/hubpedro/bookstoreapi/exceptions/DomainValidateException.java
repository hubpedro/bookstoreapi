package com.hubpedro.bookstoreapi.exceptions;

public class DomainValidateException extends RuntimeException {
	public DomainValidateException(String message) {
		super(message);
	}
}
