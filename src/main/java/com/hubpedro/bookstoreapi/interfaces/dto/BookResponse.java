package com.hubpedro.bookstoreapi.interfaces.dto;

<<<<<<< HEAD
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class BookResponse implements Serializable {

	private Long id;
	private String description;
	private String title;
	private String author;
	private String isbn;
	private BigDecimal price;

}
=======
@Builder
public class BookResponse {
	private Long id;
	private String title;
	private String author;
	private String description;
	private BigDecimal price;
	private int stock;
}
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
