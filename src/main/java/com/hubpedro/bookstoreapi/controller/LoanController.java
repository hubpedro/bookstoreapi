package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.service.impl.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

	private final LoanService loanService;

	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}

	@PostMapping("/book/{bookId}/user/{userId}")
	public ResponseEntity<LoanResponse> createLoan(@PathVariable Long userId, @PathVariable Long bookId) {
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

//    @GetMapping("/{id}")
//    public ResponseEntity<LoanResponse
//            > getUserById(@PathVariable Long id) {
//        LoanResponse
//                user = userAppService.getUserById(id);
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userAppService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<LoanResponse
//            > updateUser(@PathVariable Long id, @Valid @RequestBody LoanRequest LoanRequest) {
//        LoanResponse
//                updatedUser = userAppService.updateUser(id, LoanRequest);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<LoanResponse
//            > patchUser(@PathVariable Long id, @RequestBody LoanRequest LoanRequest) {
//        LoanResponse
//                patchedUser = userAppService.patchUser(id, LoanRequest);
//        return ResponseEntity.ok(patchedUser);
//    }
//}