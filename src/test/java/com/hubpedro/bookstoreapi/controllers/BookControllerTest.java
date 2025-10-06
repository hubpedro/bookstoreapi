package com.hubpedro.bookstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpedro.bookstoreapi.controller.BookController;
import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.serivce.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BookServiceImpl bookService;

	@MockitoBean
	private BookMapper bookMapper;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser
	void createBook_WithValidInput_ReturnsCreated() throws Exception {
		// Given
		BookRequest request = new BookRequest();
		request.setTitle("Effective Java");
		request.setAuthor("Joshua Bloch");
		// set other necessary fields

		Book book = new Book();
		book.setId(1L);
		book.setTitle("Effective Java");
		book.setDescription("Effective Java ffective Java ffective Java ffective Java ffective Java");
		book.setAuthor("Joshua Bloch");
		book.setPrice(new BigDecimal("1000"));
		book.setStock(100);

		// set other fields

		BookResponse response = new BookResponse();
		response.setId(1L);
		response.setTitle("Effective Java");
		response.setAuthor("Joshua Bloch");
		response.setDescription("Effective Java ffective Java ffective Java ffective Java ffective Java");
		response.setAuthor("Joshua Bloch");
		response.setPrice(new BigDecimal("1000"));
		response.setStock(100);
		// set other fields

		when(bookService.create(any(BookRequest.class))).thenReturn(book);
		when(bookMapper.toResponse(any(Book.class))).thenReturn(response);

		// When & Then
		mockMvc.perform(post("/api/book")
				                .with(user("usuarioTeste").roles("USER"))
				                .with(csrf()).contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.title").value("Effective Java")).andExpect(jsonPath("$.author").value("Joshua Bloch"));
	}

	@Test
	@WithMockUser
	void createBook_WithInvalidInput_ReturnsInvalid() throws Exception {
		BookRequest invalidRequest = new BookRequest("", "", "", new BigDecimal(10), 10);

		when(bookService.create(any(BookRequest.class))).thenThrow(
				new DomainValidateException("Invalid book request"));

		mockMvc.perform(post("/api/book")
				                .with(user("usuarioTeste"))
				                .with(csrf())
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(invalidRequest)))
				.andExpect(status().isBadRequest());
	}

}