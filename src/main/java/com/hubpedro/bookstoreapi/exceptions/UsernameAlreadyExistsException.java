package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

public class UsernameAlreadyExistsException extends ErrorResponseException {
	public UsernameAlreadyExistsException(String username) {
		super(HttpStatus.BAD_REQUEST);
		getBody().setDetail("O nome de usuário '" + username + "' já está em uso.");
	}
}
