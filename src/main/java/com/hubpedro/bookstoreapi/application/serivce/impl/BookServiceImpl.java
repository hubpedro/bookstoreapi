package com.hubpedro.bookstoreapi.application.serivce.impl;


import com.hubpedro.bookstoreapi.application.serivce.BookService;
import com.hubpedro.bookstoreapi.domain.model.Book;
import com.hubpedro.bookstoreapi.domain.repository.BookRepository;
import com.hubpedro.bookstoreapi.interfaces.dto.BookRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.BookResponse;
import com.hubpedro.bookstoreapi.interfaces.mapper.BookMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

	private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;
	private final BookMapper bookMapper;

	public BookServiceImpl(BookRepository bookRepository,
	                       BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper = bookMapper;
	}

	@Override
	@Transactional
	public BookResponse create(BookRequest request) {


		Book book = Book.create(request.getTitle(),
		                        request.getAuthor(),
		                        request.getDescription(),
		                        request.getPrice(),
		                        request.getStock());

		logger.info("BookServiceImpl.create[ book= ".concat(book.toString()));
		bookRepository.save(book);

		return bookMapper.toResponse(book);
	}

	@Override
	@Transactional
	public Book update(Long id,
	                   Book book) {


		Book bookUpdate = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not " +
		                                                                                             "found"));

		bookUpdate.updateFrom(book);
		logger.info("BookServiceImpl.update[ book= ".concat(bookUpdate.toString()));

		return bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book patch(Long id,
	                  Book book) {

		Book existingBook = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not " +
		                                                                                               "found"));

		existingBook.updateFrom(book);

		logger.info("BookServiceImpl.patch[ book={%s}]".concat(existingBook.toString()));

		return bookRepository.save(book);

	}

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Book book = bookRepository.findById(id)
		                          .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
		bookRepository.delete(book);
		logger.info("Deleting book with id: {}", id);
	}

}
