package com.hubpedro.bookstoreapi.controllers;

import com.hubpedro.bookstoreapi.controller.BookController;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
		value = BookController.class,
		excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BookServiceImpl bookService;

	@MockitoBean
	private BookMapper bookMapper;

	// REMOVA o mock do JwtAuthenticationFilter - não é mais necessário

	@Test
	void listAll_ReturnsListOfBooks() throws Exception {
		Book book1 = Book.create("Dom Casmurro", "Machado de Assis", "desc", BigDecimal.valueOf(1000), 100);
		Book book2 = Book.create("Memórias Póstumas", "Machado de Assis", "desc", BigDecimal.valueOf(1000), 100);
		book1.setId(1L);
		book2.setId(2L);

		when(bookService.findAll()).thenReturn(List.of(book1, book2));

		mockMvc.perform(get("/api/book"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}
}