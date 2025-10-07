package com.hubpedro.bookstoreapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.service.impl.BookServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing books.
 */
@RequestMapping("/api/book")
@RestController
public class BookController {

	private final Logger log = LoggerFactory.getLogger(BookController.class);

	private final BookServiceImpl bookService;

	private final BookMapper bookMapper;

	private final ObjectMapper objectMapper;


	public BookController(BookServiceImpl bookService, BookMapper bookMapper, ObjectMapper objectMapper) {

		this.bookService = bookService;
		this.bookMapper = bookMapper;
		this.objectMapper = objectMapper;
	}

	/**
	 * Create a new book
	 *
	 * @param request
	 * @return BookResponse
	 */
	@PostMapping
	@Operation(summary = "Create a new book", description = "Create a new book")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "201", description = "Book created successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid input")
			}
	)
	public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {

		try {
			log.debug("REST request to save Book : {}", request);

			Book createdBook = this.bookService.create(request);

			BookResponse createdBookResponse = this.bookMapper.toResponse(createdBook);

			if (null != createdBookResponse.getId()) {
				this.log.info("BookServiceImpl.create[ createdBook= {}", createdBookResponse);
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(createdBookResponse);
		} catch (DomainValidateException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping
	@Operation(summary = "List all books", description = "List all created books")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "201", description = "Listed successfully"),
					@ApiResponse(responseCode = "400", description = "Not successfully"),
					@ApiResponse(responseCode = "500", description = "Server internal error")
			}
	)
	public ResponseEntity<List<Book>> listAll() {

		List<Book> bookList = this.bookService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(bookList);
	}

}