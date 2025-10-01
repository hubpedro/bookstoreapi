package com.hubpedro.bookstoreapi.Services;

import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.serivce.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	@Mock private BookRepository bookRepository;

	@Mock private BookMapper bookMapper;

	@InjectMocks private BookServiceImpl bookService;

	@Test
	void findBookId_WhenAnyId_ShouldPass() {

		Book book = Book.create("titulotitulotitulotitulotitulo", "authorauthorauthor", "descriptiondescriptiondescriptiondescription", BigDecimal.valueOf(200),
		                        5);
		book.setId(1L);

		when(this.bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

		// act
		Book bookResult = this.bookService.findById(1L);

		assertNotNull(bookResult.getId());
		assertNotNull(bookResult.getStock());
		assertNotNull(bookResult.getTitle());
		assertNotNull(bookResult.getAuthor());
		assertNotNull(bookResult.getDescription());
		assertNotNull(bookResult.getPrice());

		assertEquals(book.getTitle(), bookResult.getTitle());
		assertEquals(book.getAuthor(), bookResult.getAuthor());
		assertEquals(book.getDescription(), bookResult.getDescription());
		assertEquals(book.getPrice(), bookResult.getPrice());
		assertEquals(book.getStock(), bookResult.getStock());

		verify(this.bookRepository, times(1)).findById(1L);
	}

	@ParameterizedTest
	@ValueSource(longs = {0, -1, -2, -3, -4})
	void findBookId_WhenAnyId_ShouldThrowException(Long invalidId)
	{

		when(bookService.findById(invalidId)).thenThrow(
				new IllegalArgumentException());
	}

	/**
	 *
	 */
	@Test
	void createBook_When_ShouldPass() {
		// //Arrange
		final Long   bookId      = 1L;
		final String title       = "3 hour study with me";
		final String author      = "hper efficient doctor";
		final String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		BigDecimal   price       = BigDecimal.valueOf(200);
		final int    stock       = 5;

		BookRequest bookRequest            = new BookRequest(title, author, description, price, stock);
		Book        bookCreatedFromRequest = Book.create(title, author, description, price, stock);
		bookCreatedFromRequest.setId(bookId);

		when(this.bookMapper.toData(any(BookRequest.class))).thenReturn(bookCreatedFromRequest);
		when(this.bookRepository.save(any(Book.class))).thenReturn(bookCreatedFromRequest);

		// Act
		Book newBook = this.bookService.create(bookRequest);

		assertNotNull(newBook.getId());
		assertNotNull(newBook.getStock());
		assertNotNull(newBook.getTitle());
		assertNotNull(newBook.getAuthor());
		assertNotNull(newBook.getDescription());
		assertNotNull(newBook.getPrice());

		assertEquals(newBook.getStock(), bookRequest.getStock());
		assertEquals(newBook.getTitle(), bookRequest.getTitle());
		assertEquals(newBook.getAuthor(), bookRequest.getAuthor());
		assertEquals(newBook.getDescription(), bookRequest.getDescription());
		assertEquals(newBook.getPrice(), bookRequest.getPrice());
		assertEquals(newBook.getStock(), bookRequest.getStock());

		verify(this.bookRepository, times(1)).save(any(Book.class));
		verify(this.bookMapper, times(1)).toData(any(BookRequest.class));

	}

	@Test
	void createBook_When_ShouldThrowException()
	{

		final Long   bookId      = 1L;
		final String title       = "3";
		final String author      = "hpe";
		final String description = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		BigDecimal   price       = BigDecimal.valueOf(0);
		final int    stock       = 5;

		BookRequest bookRequest = new BookRequest(title, author, description, price, stock);
		when(bookMapper.toData(any(BookRequest.class))).thenReturn(null);

		assertThrows(RuntimeException.class, () -> {
			bookService.create(bookRequest);
		});

		verify(bookMapper).toData(bookRequest);
		verify(bookRepository, never()).save(any(Book.class));

	}

}