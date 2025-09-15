package com.hubpedro.bookstoreapi.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
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
public class BookData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255, updatable = true, nullable = false)
	@NotBlank(message = "Title is mandatory")
	private String title;

	@Column(length = 255, updatable = true, nullable = true)
	private String description;

	@Column(length = 255, updatable = true, nullable = false)
	@NotBlank(message = "Author is mandatory")
	private String author;

	@Column(length = 255, updatable = true, nullable = true)
	private String isbn;

	@NotNull(message = "Price is mandatory")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal price;
}
