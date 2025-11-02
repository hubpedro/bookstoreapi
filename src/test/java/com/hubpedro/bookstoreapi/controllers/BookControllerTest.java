package com.hubpedro.bookstoreapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpedro.bookstoreapi.config.ProtectedEndPoints;
import com.hubpedro.bookstoreapi.controller.BookController;
import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.security.JwtAuthenticationFilter;
import com.hubpedro.bookstoreapi.service.impl.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = BookController.class,
        excludeAutoConfiguration = {ManagementWebSecurityAutoConfiguration.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtAuthenticationFilter.class)})
class BookControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private BookMapper bookMapper;.

    @MockitoBean
    private BookRepository bookRepository;

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

    @Test
    @WithMockUser(roles = "ADMIN")
    void createBook_When_ShouldPass() throws Exception {
        String title = "3 hour study with me";
        String author = "hper efficient doctor";
        String description = "This is a big description";
        BigDecimal price = BigDecimal.valueOf(1000);
        int stock = 100;
        long id = 1L;

        BookResponse bookResponse = new BookResponse(id, title, author, description, price, stock);
        BookRequest bookRequest = new BookRequest(title, author, description, price, stock);
        Book bookCreated = Book.create(bookResponse.getTitle(), bookResponse.getAuthor(), bookResponse.getDescription(), bookResponse.getPrice(), bookResponse.getStock());
        bookCreated.setId(bookResponse.getId());

        when(bookMapper.toData(any(BookRequest.class))).thenReturn(bookCreated);
        when(bookService.create(any(BookRequest.class))).thenReturn(bookCreated);
        when(bookMapper.toResponse(any(Book.class))).thenReturn(bookResponse);
        when(bookRepository.save(any(Book.class))).thenReturn(bookCreated);

        mockMvc.perform(post(ProtectedEndPoints.BOOKS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.price").value(price))
                .andExpect(jsonPath("$.stock").value(stock))
                .andExpect(jsonPath("$.available").value(true));
    }
}