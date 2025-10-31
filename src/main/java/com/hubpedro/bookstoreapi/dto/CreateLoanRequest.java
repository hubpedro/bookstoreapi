package com.hubpedro.bookstoreapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanRequest {
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    @NotNull(message = "Book ID cannot be null")
    private Long bookId;
}

