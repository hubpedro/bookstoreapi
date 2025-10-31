package com.hubpedro.bookstoreapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ProblemDetail> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
		return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
	}

	@ExceptionHandler(UserEmailAlreadyExists.class)
	public ResponseEntity<ProblemDetail> handleUserEmailAlreadyExists(UserEmailAlreadyExists ex, WebRequest request) {
		return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {

		return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
	}

    @ExceptionHandler(DomainValidateException.class)
    public ResponseEntity<ProblemDetail> handleDomainValidateException(DomainValidateException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Loan Already Returned");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
}
