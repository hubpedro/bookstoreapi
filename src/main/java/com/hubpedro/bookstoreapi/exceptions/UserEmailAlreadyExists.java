package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class UserEmailAlreadyExists extends ErrorResponseException {
	public UserEmailAlreadyExists(String email) {
		super(HttpStatus.BAD_REQUEST);
		getBody().setDetail("O email: '" + email + "' já está em uso.");

	}
}
