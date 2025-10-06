package com.hubpedro.bookstoreapi.serivce.impl;

import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.model.Loan;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.LoanRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BookServiceImpl bookService;

	public Page<Loan> findAll(Pageable pageable) {
		return loanRepository.findAll(pageable);
	}

	@Transactional
	public LoanResponse createLoan(Long userId, Long bookId) {
		User user = userService.getUserById(userId);
		Book book = bookService.findById(bookId);

		Loan loan = loanRepository.save(Loan.builder()
		                        .book(book)
		                        .user(user)
		                        .created_date(LocalDate.now())
		                        .status("AVAILABLE")
		                        .returnDate(LocalDate.now().plusMonths(1))
		                        .dueDate(LocalDate.now().plusMonths(1).plusWeeks(1)).
		                        build());


		return new LoanResponse(loan);

	}

	public Page<LoanResponse> findAllPaginated(Pageable pageable) {

		return loanRepository.findAll(pageable).map(LoanResponse::new);
	}
}