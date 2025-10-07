package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.exceptions.DomainException;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.service.BookService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

	private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;
	private final BookMapper     bookMapper;

	public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper     = bookMapper;
	}

	@Override
    @Transactional(rollbackFor = {DomainValidateException.class, DomainValidateException.class, DomainException.class})
    public Book create(BookRequest request) throws DomainException, IllegalArgumentException {

        try {
            Book bookRequested = this.bookMapper.toData(request);

            if (null == bookRequested) {
                throw new DomainException("BookRequest is invalid.");
            }

            if (this.bookRepository.existsByTitle(bookRequested.getTitle())) {
                throw new DomainException("Book with this title already exists");
            }


            Book newBok = this.bookRepository.save(bookRequested);

            this.logger.info("BookServiceImpl.create[ book= {}", newBok);

            return newBok;
        } catch (DomainValidateException e) {
            throw new IllegalArgumentException("BookRequest is invalid.", e);
        }
	}

	@Override
	@Transactional
	public Book update(Long id, Book book) {

		return this.getBook(id,book,"BookServiceImpl.update[ book= {}");
	}

	@NotNull private Book getBook (Long id,Book book,String s) {

		Optional<Book> id1        = this.bookRepository.findById(id);
		Book           bookUpdate = id1.orElseThrow(() -> new IllegalArgumentException("Book not " + "found"));

		bookUpdate.updateFrom(book);
		this.logger.info(s,bookUpdate);

		return this.bookRepository.save(book);
	}

	@Override
	@Transactional
	public Book patch(Long id, Book book) {

		return this.getBook(id,book,"BookServiceImpl.patch[ book={%s}]{}");

	}

	@Override
	public List<Book> findAll() {
		return this.bookRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {

		Optional<Book> id1  = this.bookRepository.findById(id);
		Book           book = id1.orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
		this.bookRepository.delete(book);
		this.logger.info("Deleting book with an id: {}",id);
	}

	public Book findById(Long id) {

		Optional<Book> id1 = this.bookRepository.findById(id);
		return id1.orElseThrow(() -> new IllegalArgumentException("id not found"));
	}

}