package com.hubpedro.bookstoreapi.Services;

import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.service.impl.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    private Book createValidBook(long id) {
        Book book = Book.create("Fortaleza Digital", "Dan Brown", "Thriller tecnologico sobre criptografia e segredos da NSA", BigDecimal.valueOf(200), 10);
        book.setId(id);
        return book;
    }

    @Test
    public void createBook_WithTooLongDescriptno_ShouldThrow() {
        String longDescrpition = "a".repeat(256);
        assertThrows(DomainValidateException.class, () -> {
            Book.create("Fortaleza Digital", "Dan Brown", longDescrpition, BigDecimal.valueOf(200), 10);
        });
    }

    @Test
    public void findBookId_WhenAnyId_ShouldPass() {
        String title = "Fortaleza Digital";
        String author = "Dan Brown";
        String description = "Responsável por monitorar as comunicações de todo o planeta e proteger informações do governo dos Estados Unidos.";
        BigDecimal price = BigDecimal.valueOf(200);
        int stock = 10;
        long id = 1L;

        Book book = Book.create(title, author, description, price, stock);
        book.setId(id);
        BookResponse bookResponse = new BookResponse(id, title, author, description, price, stock);

        when(bookMapper.toResponse(book)).thenReturn(bookResponse);
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // act
        BookResponse bookResult = bookService.findById(id);

        assertNotNull(bookResult);
        assertEquals(id, bookResult.getId());
        assertEquals(title, bookResult.getTitle());
        assertEquals(author, bookResult.getAuthor());
        assertEquals(description, bookResult.getDescription());
        assertEquals(price, bookResult.getPrice());

        verify(this.bookRepository, times(1)).findById(id);
	}

//	@ParameterizedTest
//	@ValueSource(longs = {0, -1, -2, -3, -4})
//	void findBookId_WhenAnyId_ShouldThrowException(Long invalidId)
//	{
//
//		when(bookRepository.findById(invalidId)).thenReturn(Optional.empty());
//
//
//		assertThrows(IllegalArgumentException.class, () -> {
//			bookService.findById(invalidId);
//		});
//
//		verify(bookRepository, times(1)).findById(invalidId);
//
//	}
//
//	/**
//	 *
//	 */
//	@Test
//	void createBook_When_ShouldPass() {
//		// //Arrange
//		final Long   bookId      = 1L;
//		final String title       = "3 hour study with me";
//		final String author      = "hper efficient doctor";
//		final String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		BigDecimal   price       = BigDecimal.valueOf(200);
//		final int    stock       = 5;
//
//		BookRequest bookRequest            = new BookRequest(title, author, description, price, stock);
//		Book        bookCreatedFromRequest = Book.create(title, author, description, price, stock);
//		bookCreatedFromRequest.setId(bookId);
//
//		when(this.bookMapper.toData(any(BookRequest.class))).thenReturn(bookCreatedFromRequest);
//		when(this.bookRepository.save(any(Book.class))).thenReturn(bookCreatedFromRequest);
//
//		// Act
//		Book newBook = this.bookService.create(bookRequest);
//
//		assertNotNull(newBook.getId());
//		assertNotNull(newBook.getStock());
//		assertNotNull(newBook.getTitle());
//		assertNotNull(newBook.getAuthor());
//		assertNotNull(newBook.getDescription());
//		assertNotNull(newBook.getPrice());
//
//		assertEquals(newBook.getStock(), bookRequest.getStock());
//		assertEquals(newBook.getTitle(), bookRequest.getTitle());
//		assertEquals(newBook.getAuthor(), bookRequest.getAuthor());
//		assertEquals(newBook.getDescription(), bookRequest.getDescription());
//		assertEquals(newBook.getPrice(), bookRequest.getPrice());
//		assertEquals(newBook.getStock(), bookRequest.getStock());
//
//		verify(this.bookRepository, times(1)).save(any(Book.class));
//		verify(this.bookMapper, times(1)).toData(any(BookRequest.class));
//
//	}
//
//	@Test
//	void createBook_When_ShouldThrowException()
//	{
//
//		final Long   bookId      = 1L;
//		final String title       = "3";
//		final String author      = "hpe";
//		final String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		BigDecimal   price       = BigDecimal.valueOf(0);
//		final int    stock       = 5;
//
//		BookRequest bookRequest = new BookRequest(title, author, description, price, stock);
//		when(bookMapper.toData(any(BookRequest.class))).thenReturn(null);
//
//		assertThrows(RuntimeException.class, () -> {
//			bookService.create(bookRequest);
//		});
//
//		verify(bookMapper).toData(bookRequest);
//		verify(bookRepository, never()).save(any(Book.class));
//
//	}
//
//	@Test
//	void deleteBookById_WhenBookExists_ShouldDeleteBook() {
//		// Arrange
//		Long bookId   = 1L;
//		Book mockBook = mock(Book.class);
//
//		when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
//		// Não é necessário configurar o delete, pois é void. Mas podemos usar verify.
//
//		// Act
//		bookService.delete(bookId);
//
//		// Assert
//		verify(bookRepository).findById(bookId);
//		verify(bookRepository).delete(mockBook);
//	}
//
//	@Test
//	void deleteBookById_WhenBookNotExists_ShouldThrowException() {
//		// Arrange
//		Long bookId = 1L;
//		when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
//
//		// Act & Assert
//		assertThrows(IllegalArgumentException.class, () -> {
//			bookService.delete(bookId);
//		});
//
//		verify(bookRepository).findById(bookId);
//		verify(bookRepository, never()).delete(any(Book.class));
//	}

}