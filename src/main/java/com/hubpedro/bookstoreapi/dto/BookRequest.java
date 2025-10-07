package com.hubpedro.bookstoreapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class BookRequest {

	@NotBlank(message = "Title cannot be blank")
	@Size(min = 5, max = 255, message = "Title must be between 5 and 255 characters")
	private String title;

	@NotBlank(message = "Author cannot be blank")
	@Size(min = 5, max = 255, message = "Author must be between 5 and 255 characters")
	private String author;

	@NotBlank(message = "Description cannot be blank")
	@Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
	private String description;

	@DecimalMin(value = "0.01", message = "Price must be greater than zero")
	private BigDecimal price;

	@Min(value = 1, message = "Stock must be at least 1")
	private int stock;

	private boolean available;

	/**
	 * @param title
	 * @param author
	 * @param description
	 * @param price
	 * @param stock
	 */
	public BookRequest(String title, String author, String description, BigDecimal price, int stock) {
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.available = stock >= 1;
	}
}