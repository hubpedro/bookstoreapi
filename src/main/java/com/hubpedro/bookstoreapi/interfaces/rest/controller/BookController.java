package com.hubpedro.bookstoreapi.interfaces.rest.controller;

import com.hubpedro.bookstoreapi.application.serivce.impl.BookServiceImpl;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/book")
@RestController
public class BookController {

	private final Logger log = LoggerFactory.getLogger(BookController.class);
	private final BookServiceImpl bookService;

	public BookController(BookServiceImpl bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	@Operation(summary = "Create a new book", description = "Create a new book")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Book created successfully"),
	                       @ApiResponse(responseCode = "400", description = "Invalid input")})
	public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
		log.debug("REST request to save Book : {}", request);
		BookResponse createdBook = bookService.create(request);
		if (createdBook.getId() != null) {
			log.info("BookServiceImpl.create[ createdBook= ".concat(createdBook.toString()));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
	}

}
