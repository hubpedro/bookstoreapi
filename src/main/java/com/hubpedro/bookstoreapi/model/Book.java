package com.hubpedro.bookstoreapi.model;

import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Entity(name = "book")
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@OneToMany(mappedBy = "book") private List<Loan> loans = new ArrayList<>();

	@ManyToMany(mappedBy = "books") private Set<User> users = new HashSet<>();

	@Size(min = 5, max = 255)
	@NotBlank
	private String title;

	@Size(max = 255) private String description;

	@NotBlank
	@Size(min = 10, max = 255)
	private String author;

	private BigDecimal price;

	@NumberFormat(style = NumberFormat.Style.NUMBER) private int stock;

	@BooleanFlag private boolean available;

	public Book() {

	}

	private Book(String title, String author, String description, BigDecimal price, int stock) {

		this.title       = title;
		this.author      = author;
		this.description = description;
		this.price       = price;
		this.stock       = stock;
		this.available   = stock >= 1;
	}

	/**
	 * Create a new book.
	 *
	 * @param title
	 * 		The title of the book. Must be between 5 and 50 characters.
	 * @param author
	 * 		The author of the book. Must be between 10 and 255 characters.
	 * @param description
	 * 		The description of the book. Must be between 10 and 255 characters.
	 * @param price
	 * 		The price of the book. Must be greater than 0.
	 * @param stock
	 * 		The stock of the book. Must be greater than 0.
	 *
	 * @return The created book. If validates correctly.
	 */
	@Contract("_, _, _, _, _ -> new")
	public static @NotNull Book create(String title, String author, String description, BigDecimal price, int stock) {

		Book newBook = new Book(validateTitle(title), validateAuthor(author), validateDescription(description), validatePrice(price), validateStock(stock));
		return newBook;
	}

	@Contract("null -> fail")
	public static String validateTitle(String title) {

		if (null != title && (title.length() > 5 && title.length() <= 255)) {
			return title;
		}
		throw new DomainValidateException("Invalid title");
	}

	@Contract("null -> fail")
	public static String validateAuthor(String author) {

        if (null != author && !author.isBlank() && author.length() > 5 && author.length() <= 255) {
			return author;
		}
		throw new DomainValidateException("Invalid author");

	}

	@Contract("null -> fail")
	private static String validateDescription(String description) {

		if (null != description && !description.isBlank() && description.length() > 10 && description.length() <= 255) {
			return description;
		}
		throw new DomainValidateException("Invalid description");
	}

	public static BigDecimal validatePrice(BigDecimal price) throws DomainValidateException {

		if (price == null) {
			throw new DomainValidateException("Price cannot be null");
		}
		if (price.compareTo(BigDecimal.ZERO) > 0) {
			return price;
		}
		throw new DomainValidateException("Price cannot be zero or negative");
	}

	@Contract("_ -> param1")
	public static int validateStock(int stock) {

		if (stock >= 1) {
			return stock;
		}
		
		throw new DomainValidateException("stock cannot be less than 0");
	}

	public void updateFrom(@NotNull Book book) {

		title       = validateTitle(book.title);
		description = validateDescription(book.description);
		author      = validateAuthor(book.author);
		price       = validatePrice(book.price);
		stock       = validateStock(book.stock);
	}

}