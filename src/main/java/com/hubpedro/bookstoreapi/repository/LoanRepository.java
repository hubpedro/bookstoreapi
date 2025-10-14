package com.hubpedro.bookstoreapi.repository;


import com.hubpedro.bookstoreapi.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
	Page<Loan> findByBook(Long id, Pageable pageable);

	Page<Loan> findByUserId(Long id, Pageable pageable);

	Optional<Loan> findById(Long id);
}