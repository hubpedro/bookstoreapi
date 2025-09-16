package com.hubpedro.bookstoreapi.domain.model;

import com.hubpedro.bookstoreapi.domain.exceptions.DomainValidateException;
import java.math.BigDecimal;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "book")
@Getter
@Setter
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Size(min = 5, max = 50)
	@NotBlank
	private String title;

	@Size(max = 255)
	private String description;

	@NotBlank
	@Size(min = 10, max = 255)
	private String author;

	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal price;

	@NumberFormat
	@NotNull
	private int stock;

	protected Book() {
	}

	private Book(String title,
			String description,
			String author,
			BigDecimal price,
			int stock) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.price = price;
		this.stock = stock;
	}

	public static Book create(
			String title,
			String author,
			String description,
			BigDecimal price,
			int stock) {
		return new Book(
				setTitle(title),
				setDescription(description),
				setAuthor(author),
				setPrice(price),
				stock);
	}

	private static String setTitle(String title) {
		if (title != null && (title.length() > 5 && title.length() <= 50)) {
			return title;
		}
		throw new DomainValidateException("Invalid title");
	}

	private static String setDescription(String description) {
		if (description != null && !description.isBlank() && description.length() > 10 && description.length() <= 255) {
			return description;
		}
		throw new DomainValidateException("Invalid description");
	}

	private static String setAuthor(String author) {
		if (author != null && !author.isBlank() && author.length() > 10 && author.length() <= 255) {
			return author;
		}
		throw new DomainValidateException("Invalid author");

	}

	private static BigDecimal setPrice(BigDecimal price) {
		if (price != null && price.compareTo(BigDecimal.ZERO) > 0) {
			return price;
		}
		throw new DomainValidateException("Invalid price");
	}

	public void updateFrom(Book book) {
		this.title = setTitle(book.getTitle());
		this.description = setDescription(book.getDescription());
		this.author = setAuthor(book.getAuthor());
		this.price = setPrice(book.getPrice());
		this.stock = book.getStock();
	}

}