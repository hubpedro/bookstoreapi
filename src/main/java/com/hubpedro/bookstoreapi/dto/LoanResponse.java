package com.hubpedro.bookstoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {

	private Long userId;
	private Long id;
	private Long bookId;
	private LocalDate createdAt;
	private LocalDate lastModifiedAt;
	private LocalDate loanedAt;
	private LocalDate dueOn;
	private LocalDate returnedAt;
	private String    status;
	private BigDecimal loanDebt;
}