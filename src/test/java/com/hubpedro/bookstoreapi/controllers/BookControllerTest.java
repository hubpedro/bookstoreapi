package com.hubpedro.bookstoreapi.controllers;

import com.hubpedro.bookstoreapi.config.ProtectedEndPoints;
import com.hubpedro.bookstoreapi.controller.BookController;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.security.JwtAuthenticationFilter;
import com.hubpedro.bookstoreapi.service.impl.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
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
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                ManagementWebSecurityAutoConfiguration.class
        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtAuthenticationFilter.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BookService bookService;

	@MockitoBean
	private BookMapper bookMapper;

    @MockitoBean
    private BookRepository bookRepository;

    // REMOVA o mock do JwtAuthenticationFilter - não é mais necessário

	@Test
	void listAll_ReturnsListOfBooks() throws Exception {
        Book book1 = Book.create("Dom Casmurro", "Machado de Assis", "description ultra description big big",
                BigDecimal.valueOf(1000), 100);
        Book book2 = Book.create("Memórias Póstumas", "Machado de Assis", "description ultra description big big",
                BigDecimal.valueOf(1000), 100);
		book1.setId(1L);
		book2.setId(2L);

		when(bookService.findAll()).thenReturn(List.of(book1, book2));

        mockMvc.perform(get(ProtectedEndPoints.BOOKS))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2));
	}

//	@Test
//	void createBook_When_ShouldPass() throws Exception {
//		String title = "3 hour study with me";
//		String author = "hper efficient doctor";
//		String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		BigDecimal price = BigDecimal.valueOf(1000);
//		int stock = 100;
//
//		BookRequest bookRequest = new BookRequest(title, author, description, price, stock);
//		BookResponse bookResponse = new BookResponse(1L, title, author, description, price, stock);
//		Book bookCreated = Book.create(title, author, description, price, stock);
//		bookCreated.setId(1L);
//
//		when(bookMapper.toData(any(BookRequest.class))).thenReturn(bookCreated);
//		when(bookService.create(any(BookRequest.class))).thenReturn(bookCreated);
//		when(bookMapper.toResponse(any(Book.class))).thenReturn(bookResponse);
//		when(bookRepository.save(any(Book.class))).thenReturn(bookCreated);
//
//		mockMvc.perform(post(ApiPaths.BOOKS))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.length()").value(1))
//				.andExpect(jsonPath("$[0].id").value(1))
//				.andExpect(jsonPath("$[0].title").value(title))
//				.andExpect(jsonPath("$[0].author").value(author))
//				.andExpect(jsonPath("$[0].description").value(description))
//				.andExpect(jsonPath("$[0].price").value(price))
//				.andExpect(jsonPath("$[0].stock").value(stock))
//				.andExpect(jsonPath("$[0].available").value(true));
//	}
}