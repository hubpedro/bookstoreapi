package com.hubpedro.bookstoreapi.interfaces.dto;

<<<<<<< HEAD
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
=======

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
import lombok.Setter;

@Getter
@Setter
<<<<<<< HEAD
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

=======
@Builder
public class BookRequest {
	private String title;
	private String author;
	private String description;
	private BigDecimal price;
	private int stock;
	private boolean available;
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
}
