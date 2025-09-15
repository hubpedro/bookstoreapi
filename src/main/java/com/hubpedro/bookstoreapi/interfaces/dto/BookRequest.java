package com.hubpedro.bookstoreapi.interfaces.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest implements Serializable {

	private String title;
	private String description;
	private String author;
	private String isbn;
	private BigDecimal price;


	@Override
	public String toString() {
		return "BookRequest [title=" + title + ", author=" + author + ", isbn=" + isbn + ", price=" + price + "]";
	}

}
