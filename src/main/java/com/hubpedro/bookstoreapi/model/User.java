package com.hubpedro.bookstoreapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity(name = "users")
@Getter
@Setter
public class User implements UserDetails {

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
			name = "user_books", joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private Set<Book> books = new HashSet<>();

	@OneToMany(mappedBy = "user")
	@JsonManagedReference // Indica o lado "gerenciado" (serializado)
	private List<Loan> loans = new ArrayList<>();


	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles = new HashSet<>();

	private User(String name, String email, String password) throws DomainValidateException {

		this.name     = validateAndSetName(name);
		this.email    = validateAndSetEmail(email);
		this.password = validateAndSetPassword(password);
	}

	protected User() {

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

	private static String validateAndSetName(String name) throws DomainValidateException {

		if (name != null && (name.length() >= 5 && name.length() <= 50)) {
			return name;
		}
		throw new DomainValidateException("Invalid name");
	}

	/**
	 * @param name
	 * @param email
	 * @param password
	 * @return User
	 * @throws DomainValidateException
	 */
	public static @NotNull User create(String name, String email, String password) throws DomainValidateException {

		return new User(validateAndSetName(name), validateAndSetEmail(email), validateAndSetPassword(password));
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

		return "User[" + "id={}" + "name={}" + "email={}" + "password={}";
	}

	/**
	 * Returns the authorities granted to the user. Cannot return <code>null</code>.
	 *
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				       .map(roles -> new SimpleGrantedAuthority("ROLE_" + roles.toUpperCase()))
				       .collect(Collectors.toSet());
	}

	/**
	 * Returns the username used to authenticate the user. Cannot return
	 * <code>null</code>.
	 *
	 * @return the username (never <code>null</code>)
	 */
	@Override
	public String getUsername() {
		return this.name;
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}