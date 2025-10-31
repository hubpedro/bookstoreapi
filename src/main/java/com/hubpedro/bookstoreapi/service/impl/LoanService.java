package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.dto.CreateLoanRequest;
import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.exceptions.BookNotAvailableException;
import com.hubpedro.bookstoreapi.exceptions.BookNotFoundException;
import com.hubpedro.bookstoreapi.exceptions.LoanNotFoundException;
import com.hubpedro.bookstoreapi.exceptions.UserNotFoundException;
import com.hubpedro.bookstoreapi.mapper.LoanMapper;
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

/**
 * Service class for handling loan-related operations.
 */
@Service
public class LoanService {

	private final LoanRepository    loanRepository;
	private final UserRepositoryJPA userRepository;
	private final BookRepository    bookRepository;
    private final LoanMapper loanMapper;


    /**
     * Constructs a new LoanService with the required repositories and mapper.
     *
     * @param loanRepository The repository for loan data access.
     * @param userService    The repository for user data access.
     * @param bookRepository The repository for book data access.
     * @param loanMapper     The mapper for converting between Loan entities and DTOs.
     */
    public LoanService(final LoanRepository loanRepository, final UserRepositoryJPA userService, final BookRepository bookRepository, LoanMapper loanMapper) {
		this.loanRepository = loanRepository;
        userRepository = userService;
		this.bookRepository = bookRepository;
        this.loanMapper = loanMapper;
    }

    /**
     * Creates a new loan for a given user and book.
     * It handles finding the user and book, checking book availability,
     * updating the book's stock, and saving the new loan.
     *
     * @param request The ID of the user borrowing the book.
     * @return A {@link LoanResponse} DTO representing the newly created loan.
     * @throws UserNotFoundException if the user with the given ID is not found.
     * @throws BookNotFoundException if the book with the given ID is not found.
     */
    @Transactional
    public LoanResponse createLoan(final CreateLoanRequest request) {
        final User user = this.userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        final Book book = this.bookRepository.findById(request.getBookId()).orElseThrow(() -> new BookNotFoundException("Book id " + "not found"));

        if (book.getStock() < 1) {
            throw new BookNotAvailableException("Book is out of stock");
        }

        book.processLoan();

        final Loan loan = this.loanRepository.save(Loan.builder()
                .book(book).user(user).loanedAt(LocalDate.now())
                .dueOn(LocalDate.now().plusWeeks(2))
                .build());
        return loanMapper.toResponse(loan);

    }

    /**
     * Processes the return of a loaned book.
     * This method finds the loan, delegates the return process (including fine calculation)
     * to the Loan entity, and updates the book's stock.
     *
     * @param loanId The ID of the loan to be returned.
     * @return A {@link LoanResponse} DTO representing the updated loan.
     * @throws LoanNotFoundException if the loan with the given ID is not found.
     * @throws IllegalStateException if the loan has already been returned.
     */
    @Transactional
    public LoanResponse loanReturn(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with id: " + loanId));

        loan.processReturn(LocalDate.now());
        loan.getBook().processReturn();

        return loanMapper.toResponse(loan);
    }

    public Page<LoanResponse> findAllPaginated(final Pageable pageable) {

        return this.loanRepository.findAll(pageable).map(this.loanMapper::toResponse);
	}
}