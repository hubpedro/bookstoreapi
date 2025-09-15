package com.hubpedro.bookstoreapi.interfaces.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse implements Serializable {

	private Long id;
	private String description;
	private String title;
	private String author;
	private String isbn;
	private BigDecimal price;

}