package com.hubpedro.bookstoreapi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {
	private Long      user;
	private Long      book;
	private LocalDate created_date;
	private LocalDate last_modified;
	private LocalDate loanDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String    status;
}

