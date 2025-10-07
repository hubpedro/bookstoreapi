package com.hubpedro.bookstoreapi.service;


import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.model.Book;

import java.util.List;

public interface BookService {
    Book create(BookRequest book);

	Book update(Long id,
	            Book book);

	Book patch(Long id,
	           Book book);

	List<Book> findAll();

	void delete(Long id);


	Book findById(Long id);

}
