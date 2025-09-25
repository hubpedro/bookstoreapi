package com.hubpedro.bookstoreapi.mapper;


import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

	public Book toData(BookRequest bookRequest) {
		return Book.create(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getDescription(),
		                   bookRequest.getPrice(), bookRequest.getStock());
	}

	public BookResponse toResponse(Book book) {
		return new BookResponse(
				book.getId(),
				book.getTitle(),
				book.getAuthor(),
				book.getDescription(),
				book.getPrice(),
				book.getStock()
		);
	}

}
