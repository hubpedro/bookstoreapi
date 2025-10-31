package com.hubpedro.bookstoreapi.mapper;


import com.hubpedro.bookstoreapi.dto.LoanResponse;
import com.hubpedro.bookstoreapi.model.Loan;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public
class LoanMapper {
	public LoanResponse toResponse(Loan loan) {
		return LoanResponse.builder()
				.id(loan.getId())
				.userId(loan.getUser().getId())
				.bookId(loan.getBook().getId())
				.status(loan.getStatus().name())
				.loanedAt(loan.getLoanedAt())
				.dueOn(loan.getDueOn())
				.returnedAt(loan.getReturnedAt())
				.loanDebt(loan.getLoanDebt())
				.createdAt(loan.getCreatedAt())
				.lastModifiedAt(loan.getLastModifiedAt())
				.build();
	}

}
