package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.dto.BookRequest;
import com.hubpedro.bookstoreapi.dto.BookResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainException;
import com.hubpedro.bookstoreapi.mapper.BookMapper;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.service.IBookService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public
class BookService implements IBookService {

	private final Logger logger = LoggerFactory.getLogger(BookService.class);

	private final BookRepository bookRepository;
	private final BookMapper     bookMapper;

	public
	BookService(BookRepository bookRepository, BookMapper bookMapper) {
		this.bookRepository = bookRepository;
		this.bookMapper     = bookMapper;
	}

	@Override
    @Transactional(rollbackFor = {DomainException.class})
    public Book create(BookRequest request) throws DomainException {

        Book bookRequested = this.bookMapper.toData(request);

        if (null == bookRequested) {
            throw new DomainException("BookRequest is invalid.");
        }

        if(this.bookRepository.findByTitle(bookRequested.getTitle())) {
            throw new DomainException("Book with this title already exists");
        }


        Book newBok = this.bookRepository.save(bookRequested);

        this.logger.info("BookServiceImpl.create[ book= {}", newBok);

        return newBok;
	}

	@Override
	@Transactional
	public Book update(Long id, Book book) {

		return this.updateOrPatch(id,book,"BookServiceImpl.update[ book= {}");
	}

	@NotNull
    private Book updateOrPatch(Long id, Book book, String logMessage) {
        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        bookToUpdate.updateFrom(book);
        this.logger.info(logMessage, bookToUpdate);

        return this.bookRepository.save(bookToUpdate);
    }

	@Override
	@Transactional
	public Book patch(Long id, Book book) {

		return this.updateOrPatch(id,book,"BookServiceImpl.patch[ book={}]");

	}

	@Override
	public List<Book> findAll() {
		return this.bookRepository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {

		Book book = this.bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
		this.bookRepository.delete(book);
		this.logger.info("Deleting book with an id: {}",id);
	}

	public
	BookResponse findById(Long id) {
		Book book = this.bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
		return this.bookMapper.toResponse(book);
	}

}