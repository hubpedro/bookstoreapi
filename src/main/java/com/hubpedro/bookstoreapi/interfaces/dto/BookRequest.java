package com.hubpedro.bookstoreapi.interfaces.dto;


import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookRequest {
	private String title;
	private String author;
	private String description;
	private BigDecimal price;
	private int stock;
	private boolean available;
}
