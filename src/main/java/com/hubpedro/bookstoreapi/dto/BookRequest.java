package com.hubpedro.bookstoreapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class BookRequest {
	private String     title;
	private String     author;
	private String     description;
	private BigDecimal price;
	private int        stock;
	private boolean    available;

    /**
     * @param title
     * @param author
     * @param description
     * @param price
     * @param stock
     */
	public BookRequest (String title,String author,String description,BigDecimal price,int stock) {
		this.title       = title;
		this.author      = author;
		this.description = description;
		this.price       = price;
		this.stock       = stock;
        this.available = stock >= 1;
    }

}