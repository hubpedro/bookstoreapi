package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.serivce.impl.BookServiceImpl;
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

	private final Logger          log = LoggerFactory.getLogger(BookController.class);
	private final BookServiceImpl bookService;

	private final BookMapper bookMapper;

	public BookController (BookServiceImpl bookService,BookMapper bookMapper) {
		this.bookService = bookService;
		this.bookMapper = bookMapper;
	}

	@PostMapping
	@Operation(summary = "Create a new book", description = "Create a new book")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Book created successfully"),
	                       @ApiResponse(responseCode = "400", description = "Invalid input")})
	public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
		log.debug("REST request to save Book : {}", request);

		Book createdBook = this.bookService.create(request);

		BookResponse createdBookResponse = this.bookMapper.toResponse(createdBook);

		if ( null != createdBookResponse.getId() ) {
			this.log.info("BookServiceImpl.create[ createdBook= " + createdBookResponse);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdBookResponse);
	}

}
