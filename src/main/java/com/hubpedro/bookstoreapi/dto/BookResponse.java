package com.hubpedro.bookstoreapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookResponse implements Serializable {

	private Long       id;
	private String     title;
	private String     author;
	private String     description;
	private BigDecimal price;
	private int        stock;

}
