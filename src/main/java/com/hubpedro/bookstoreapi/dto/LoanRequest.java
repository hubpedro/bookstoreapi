package com.hubpedro.bookstoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {
	private Long userId;
	private Long bookId;
	private LocalDate created_date;
	private LocalDate last_modified;
	private LocalDate loanDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String    status;
}

