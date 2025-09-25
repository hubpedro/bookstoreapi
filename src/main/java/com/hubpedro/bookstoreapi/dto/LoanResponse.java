package com.hubpedro.bookstoreapi.dto;

import com.hubpedro.bookstoreapi.model.Book;
import com.hubpedro.bookstoreapi.model.User;
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
	private Long      id;
	private User      user;
	private Book      book;
	private LocalDate created_date;
	private LocalDate last_modified;
	private LocalDate loanDate;
	private LocalDate dueDate;
	private LocalDate returnDate;
	private String    status;
}

