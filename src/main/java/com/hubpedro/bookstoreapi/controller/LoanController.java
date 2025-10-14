package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.service.impl.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/book/{bookId}/user/{userId}")
    public ResponseEntity<LoanResponse> loanCreation(@PathVariable Long userId, @PathVariable Long bookId) {
		LoanResponse loanResponse = loanService.createLoan(userId, bookId);
		return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
	}

	@GetMapping
	public ResponseEntity<Page<LoanResponse>> getAllLoan(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) throws RuntimeException
	{
		Pageable pageable = PageRequest.of(page, size, Sort.by("id")); // Ordenação explícita
		Page<LoanResponse> loanPage = loanService.findAllPaginated(pageable);
		return ResponseEntity.ok(loanPage);
		}

	}