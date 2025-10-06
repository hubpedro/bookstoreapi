package com.hubpedro.bookstoreapi.dto;

import com.hubpedro.bookstoreapi.model.Loan;
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
public class LoanResponse {

	private Long userId;
	private Long      id;

	private Long bookId;

	public LoanResponse(Loan loan) {

		this.id            = loan.getId();
		this.userId        = loan.getUser().getId();
		this.bookId        = loan.getBook().getId();
		this.created_date  = loan.getCreated_date();
		this.last_modified = loan.getLast_modified();
		this.loanDate      = loan.getLoanDate();
		this.dueDate       = loan.getDueDate();
		this.returnDate    = loan.getReturnDate();
		this.status        = loan.getStatus();
	}
	private LocalDate created_date;
	private LocalDate last_modified;
	private LocalDate loanDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String    status;
}