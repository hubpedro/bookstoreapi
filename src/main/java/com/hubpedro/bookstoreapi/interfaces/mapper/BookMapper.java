package com.hubpedro.bookstoreapi.interfaces.mapper;


import com.hubpedro.bookstoreapi.application.dto.BookData;
import com.hubpedro.bookstoreapi.domain.model.Book;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

	public BookData toData(BookRequest bookRequest) {
		return BookData.builder()
		               .title(bookRequest.getTitle())
		               .author(bookRequest.getAuthor())
		               .description(bookRequest.getDescription())
		               .isbn(bookRequest.getIsbn())
		               .price(bookRequest.getPrice())
		               .build();
	}

	public BookResponse toResponse(Book book) {
		return BookResponse.builder()
		                   .id(book.getId())
		                   .title(book.getTitle())
		                   .author(book.getAuthor())
		                   .description(book.getDescription())
		                   .isbn(book.getIsbn())
		                   .price(book.getPrice())
		                   .build();
	}
}
