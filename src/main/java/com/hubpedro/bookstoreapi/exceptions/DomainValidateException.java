package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainValidateException extends ErrorResponseException {

    public DomainValidateException(String reason) {
        super(HttpStatus.BAD_REQUEST);
    }
}
