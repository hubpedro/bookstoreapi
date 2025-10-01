package com.hubpedro.bookstoreapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookRequest {

	private String     title;

	private String     author;

	private String     description;

	private BigDecimal price;

	private int        stock;
	private boolean    available;

	public BookRequest (String title,String author,String description,BigDecimal price,int stock) {

		super();

		this.title       = title;
		this.author      = author;
		this.description = description;
		this.price       = price;
		this.stock       = stock;
		this.available   = 1 <= stock;
	}

}
