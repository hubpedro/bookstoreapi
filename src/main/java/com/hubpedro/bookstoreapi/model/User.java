package com.hubpedro.bookstoreapi.model;

import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Size(min = 5, max = 50)
	private String name;

	@Email
	private String email;

	@Size(min = 8, max = 255)
	private String password;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "user_books",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private Set<Book> books = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private List<Loan> loans = new ArrayList<>();


	private User(String name, String email, String password) throws DomainValidateException {
		if (this.validate(name, email, password)) {
			throw new DomainValidateException("validation error");
		}
		this.name     = validateAndSetName(name);
		this.email    = validateAndSetEmail(email);
		this.password = validateAndSetPassword(password);
	}

	private static String validateAndSetName(String name) throws DomainValidateException {
		if (name != null && (name.length() > 5 && name.length() <= 50)) {
			return name;
		}
		throw new DomainValidateException("Invalid name");
	}

	private static String validateAndSetEmail(String email) throws DomainValidateException {
		if (email != null && email.matches("^[A-Za-z0-9]+@(.+)$")) {
			return email;
		}
		throw new DomainValidateException("Invalid email");
	}

	private static String validateAndSetPassword(String password) throws DomainValidateException {
		if (password != null && password.length() >= 8 && password.length() <= 255) {
			return password;
		}
		throw new DomainValidateException("Invalid password");
	}

	private boolean validate(String name, String email, String password) throws DomainValidateException {
		return this.validateNameSpecificRules(name) && this.validateEmailSpecificRules(email) && this.validatePasswordSpecificRules(password);
	}

	private boolean validateNameSpecificRules(String name) throws DomainValidateException {
		return this.name != null && (name.length() > 5 && name.length() <= 50);
	}

	private boolean validateEmailSpecificRules(String email) throws DomainValidateException {
		return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

	private boolean validatePasswordSpecificRules(String password) throws DomainValidateException {
		return password != null && password.length() >= 8 && password.length() <= 255;
	}

	protected User() {

	}

	public static User create(String name, String email, String password) {
		return new User(validateAndSetName(name), validateAndSetEmail(email), validateAndSetPassword(password));
	}

	public void addBook(Book book) {
		this.books.add(book);
		book.getUsers().add(this);
	}

	public void removeBook(Book book) {
		this.books.remove(book);
		book.getUsers().remove(this);
	}

	public void updateFrom(User user) {
		this.name     = validateAndSetName(user.getName());
		this.email    = validateAndSetEmail(user.getEmail());
		this.password = validateAndSetPassword(user.getPassword());
	}

	@Override
	public String toString() {
		return "User[" +
		       "id={}" +
		       "name={}" +
		       "email={}" +
		       "password={}";
	}
}