package com.hubpedro.bookstoreapi.model;

import com.hubpedro.bookstoreapi.exceptions.BookNotAvailableException;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Book entity in the bookstore.
 */
@Slf4j
@Entity(name = "book")
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    private Set<User> users = new HashSet<>();

	@NotBlank
    @Size(min = 6, max = 255)
	private String title;

    @NotBlank
    @Size(min = 11, max = 255)
    private String description;

	@NotBlank
    @Size(min = 6, max = 255)
	private String author;

	private BigDecimal price;

	@NumberFormat(style = NumberFormat.Style.NUMBER)
	private int stock;

	private boolean available;

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


	public static @NotNull Book create(String title, String author, String description, BigDecimal price, int stock) {

        return new Book(validateTitle(title), validateAuthor(author), validateDescription(description), validatePrice(price), validateStock(stock));
	}

	public static String validateTitle(String title) {
        if (title != null && !title.isBlank() && (title.length() > 5 && title.length() <= 255)) {
			return title;
		}
		throw new DomainValidateException("Invalid title");
    }

    public static String validateAuthor(String author) {

        if (null != author && !author.isBlank() && author.length() > 5 && author.length() <= 255) { // Mantendo a regra original de > 5
			return author;
		}
		throw new DomainValidateException("Invalid author");

    }

    public static String validateDescription(String description) {

		if (null != description && !description.isBlank() && description.length() > 10 && description.length() <= 255) {
			return description;
		}
		throw new DomainValidateException("Invalid description");
    }

	public static BigDecimal validatePrice(BigDecimal price) throws DomainValidateException {

        if (price == null || price.compareTo(BigDecimal.ZERO) == 0) {
            throw new DomainValidateException("Price cannot be null, zero or negative");
		}
			return price;
    }

    /**
     * Validates the book stock quantity.
     *
     * @param stock The stock quantity to validate.
     * @return The validated stock quantity.
     * @throws DomainValidateException if the stock is less than 1.
     */
	public static int validateStock(int stock) {
		if (stock >= 1) {
			return stock;
		}
		throw new DomainValidateException("stock cannot be less than 0");
    }

    /**
     * Updates the current book's properties from another book object,
     * applying validation to each property.
     *
     * @param book The book object containing the new values.
     */
	public void updateFrom(@NotNull Book book) {

        title = validateTitle(book.getTitle());
        description = validateDescription(book.getDescription());
        author = validateAuthor(book.getAuthor());
        price = validatePrice(book.getPrice());
        stock = validateStock(book.getStock());
		available = this.stock >= 1;
    }

    /**
     * Processes a loan for this book by decrementing the stock.
     * Updates the availability status based on the new stock count.
     *
     * @throws BookNotAvailableException if the book is out of stock.
     */
    public void processLoan() {
		if(this.stock < 1) {
			throw new BookNotAvailableException("Book not available");
		}
		this.stock--;
		this.available = this.stock >= 1;
    }

    /**
     * Processes a return for this book by incrementing the stock.
     * Sets the book as available.
     */
    public void processReturn() {
        this.stock++;
        this.available = true;
        log.info("Livro ID {} teve estoque atualizado para {}", this.id, this.stock);
    }

    public void ensureStockIsAvailable() {
        if (this.stock < 1) {
            throw new BookNotAvailableException("Book is out of stock");
        }
    }
}