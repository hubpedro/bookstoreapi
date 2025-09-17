package com.hubpedro.bookstoreapi.domain.exceptions;

public class DomainValidateException extends RuntimeException {
	public DomainValidateException(String message) {
		super(message);
	}
}
