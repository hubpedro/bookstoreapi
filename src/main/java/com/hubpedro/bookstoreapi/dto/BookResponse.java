package com.hubpedro.bookstoreapi.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookResponse implements Serializable {
	private Long       id;
	private String     title;
	private String     author;
	private String     description;
	private BigDecimal price;
	private int        stock;
    private boolean available;

    public BookResponse(Long id, String title, String author, String description, BigDecimal price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.available = stock >= 1;
    }
}