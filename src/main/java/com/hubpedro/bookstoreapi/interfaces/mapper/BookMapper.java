package com.hubpedro.bookstoreapi.interfaces.mapper;


<<<<<<< HEAD
import com.hubpedro.bookstoreapi.application.dto.BookData;
=======
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
import com.hubpedro.bookstoreapi.domain.model.Book;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

<<<<<<< HEAD
	public BookData toData(BookRequest bookRequest) {
		return BookData.builder()
		               .title(bookRequest.getTitle())
		               .author(bookRequest.getAuthor())
		               .description(bookRequest.getDescription())
		               .isbn(bookRequest.getIsbn())
		               .price(bookRequest.getPrice())
		               .build();
=======
	public Book toData(BookRequest bookRequest) {
		return Book.create(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getDescription(),
				bookRequest.getPrice(), bookRequest.getStock());
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
	}

	public BookResponse toResponse(Book book) {
		return BookResponse.builder()
<<<<<<< HEAD
		                   .id(book.getId())
		                   .title(book.getTitle())
		                   .author(book.getAuthor())
		                   .description(book.getDescription())
		                   .isbn(book.getIsbn())
		                   .price(book.getPrice())
		                   .build();
	}
=======
				.title(book.getTitle())
				.author(book.getAuthor())
				.description(book.getDescription())
				.price(book.getPrice())
				.stock(book.getStock())
				.build();
	}

>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
}
