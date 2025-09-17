package com.hubpedro.bookstoreapi.application.serivce;


import com.hubpedro.bookstoreapi.domain.model.Book;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import java.util.List;

public interface BookService {
	BookResponse create(BookRequest book);

	Book update(Long id,
	            Book book);

	Book patch(Long id,
	           Book book);

	List<Book> findAll();

	void delete(Long id);


}
