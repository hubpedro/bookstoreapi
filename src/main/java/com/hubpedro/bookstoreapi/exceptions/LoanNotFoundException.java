package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoanNotFoundException extends ErrorResponseException {

    public LoanNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND); // Usamos 404 Not Found, que é mais apropriado
        getBody().setDetail(message);
    }
}