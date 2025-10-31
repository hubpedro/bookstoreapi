package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ErrorResponseException {

	public UserNotFoundException(String message) {

		super(HttpStatus.BAD_REQUEST);
		getBody().setDetail(message);
	}

}