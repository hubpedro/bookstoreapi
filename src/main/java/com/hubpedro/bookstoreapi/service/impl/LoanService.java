package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.exceptions.BookNotFoundException;
import com.hubpedro.bookstoreapi.exceptions.UserNotFoundException;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.model.Loan;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.BookRepository;
import com.hubpedro.bookstoreapi.repository.LoanRepository;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public
class LoanService {

	private final LoanRepository    loanRepository;
	private final UserRepositoryJPA userRepository;
	private final BookRepository    bookRepository;


	public
	LoanService(LoanRepository loanRepository, UserRepositoryJPA userService, BookRepository bookRepository) {
		this.loanRepository = loanRepository;
		this.userRepository = userService;
		this.bookRepository = bookRepository;
	}

	public
	Page<Loan> findAll(Pageable pageable) {
		return loanRepository.findAll(pageable);
	}

	@Transactional
	public LoanResponse createLoan(Long userId, Long bookId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not " + "found"));
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Book id " + "not found"));

		book.processLoan();

		Loan loan = loanRepository.save(Loan.builder()
		                                    .book(book).user(user).loanDate(LocalDate.now()) // Data que o empréstimo foi feito
		                                    .status("ACTIVE") // Um empréstimo recém-criado está ativo
		                                    .dueDate(LocalDate.now().plusWeeks(2))
		                                    .build());
		return new LoanResponse(loan);

	}

	@Transactional
	public void loanReturn(Long loanId) {
		Loan loan = loanRepository.

	}

	public
	Page<LoanResponse> findAllPaginated(Pageable pageable) {

		return loanRepository.findAll(pageable).map(LoanResponse::new);
	}
}