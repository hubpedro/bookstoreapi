package com.hubpedro.bookstoreapi.interfaces.mapper;


import com.hubpedro.bookstoreapi.domain.model.Book;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

	public Book toData(BookRequest bookRequest) {
		return Book.create(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getDescription(),
				bookRequest.getPrice(), bookRequest.getStock());
	}

	public BookResponse toResponse(Book book) {
		return BookResponse.builder()
				.title(book.getTitle())
				.author(book.getAuthor())
				.description(book.getDescription())
				.price(book.getPrice())
				.stock(book.getStock())
				.build();
	}

}
