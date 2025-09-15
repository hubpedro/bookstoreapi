package com.hubpedro.bookstoreapi.domain.model;

import com.hubpedro.bookstoreapi.shared.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.Getter;

@Getter
@Entity(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255, updatable = true, nullable = false)
	@NotBlank(message = "Title is mandatory")
	private String title;

	@Column(length = 255, updatable = true, nullable = true)
	private String description;

	@Column(length = 255, updatable = true, nullable = false)
	@NotBlank(message = "Author is mandatory")
	private String author;

	@Column(length = 255, updatable = true, nullable = true)
	private String isbn;

	@NotNull(message = "Price is mandatory")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal price;

	protected Book() {}

	private Book(String title,
	             String description,
	             String author,
	             String isbn,
	             BigDecimal price) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
	}

	public static Book create(String title,
	                          String description,
	                          String author,
	                          String isbn,
	                          BigDecimal price) {
		Book book = new Book(title, description, author, isbn, price);
		book.validate(book);
		return book;
	}

	private void validate(Book book) {
		setTitle(book.getTitle());
		setDescription(book.getDescription());
		setAuthor(book.getAuthor());
		setIsbn(book.getIsbn());
		setPrice(book.getPrice());
	}

	public void setTitle(String title) {
		this.title = Validations.notBlank(title, "Title cannot be null or empty");
		this.title = Validations.maxLength(title, 100, "Title too long");
	}

	public void setDescription(String description) {
		this.description = Validations.maxLength(description, 500, "Description too long");
	}

	public void setAuthor(String author) {
		this.author = Validations.maxLength(author, 100, "Author too long");
		this.author = Validations.notBlank(author, "Author cannot be null or empty");
	}

	public void setIsbn(String isbn) {
		// Validação básica de ISBN - pode ser aprimorada
		if (isbn != null && !isbn.matches("^[0-9-]+$")) {
			throw new IllegalArgumentException("ISBN must contain only numbers and hyphens");
		}
		this.isbn = isbn;
	}

	public void setPrice(BigDecimal price) {
		this.price = Validations.notNull(price, "Price cannot be null");
		this.price = Validations.minValue(price, BigDecimal.ZERO, "Price must be positive");
	}

	public void updateFrom(Book source) {
		updateFieldIfNotNull(source::getTitle, this::setTitle);
		updateFieldIfNotNull(source::getDescription, this::setDescription);
		updateFieldIfNotNull(source::getAuthor, this::setAuthor);
		updateFieldIfNotNull(source::getIsbn, this::setIsbn);
		updateFieldIfNotNull(source::getPrice, this::setPrice);
	}

	private <T> void updateFieldIfNotNull(Supplier<T> getter,
	                                      Consumer<T> setter) {
		T value = getter.get();
		if (value != null) {
			setter.accept(value);
		}
	}
}
