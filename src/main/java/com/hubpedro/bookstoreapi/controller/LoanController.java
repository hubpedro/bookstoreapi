package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.dto.CreateLoanRequest;
import com.hubpedro.bookstoreapi.dto.LoanRequest;
import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.service.impl.LoanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * REST controller for managing book loans.
 */
@RestController
@RequestMapping("/api/loans")
public
class LoanController {

	private final LoanService loanService;

    public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

    /**
     * Creates a new loan. The user ID and book ID are provided in the request body.
     * Note: The path variables {bookId} and {userId} are defined but not used in the method signature.
     * The data is taken from the {@link LoanRequest} body.
     *
     * @param request DTO containing the user ID and book ID.
     * @return A {@link ResponseEntity} with status 201 (Created) and the created loan details in the body.
     * The 'Location' header contains the URL to the newly created loan.
     */
    @PostMapping
    public ResponseEntity<LoanResponse> loanCreation(@RequestBody @Valid CreateLoanRequest request) {
        LoanResponse loanResponse = loanService.createLoan(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loanResponse.getId()).toUri();
        return ResponseEntity.created(location).body(loanResponse);
    }


    /**
     * Retrieves a paginated list of all loans.
     *
     * @param page The page number to retrieve (default is 0).
     * @param size The number of loans per page (default is 10).
     * @return A {@link ResponseEntity} with status 200 (OK) and a page of {@link LoanResponse} objects.
     */
    @GetMapping
    public ResponseEntity<Page<LoanResponse>> getAllLoan(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws RuntimeException
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("id")); // Ordenação explícita
		Page<LoanResponse> loanPage = loanService.findAllPaginated(pageable);
		return ResponseEntity.ok(loanPage);
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnBook(@PathVariable Long id) {
        LoanResponse updatedLoan = loanService.loanReturn(id);
        return ResponseEntity.ok(updatedLoan);

    }

}