package com.hubpedro.bookstoreapi.domain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.results.DomainResultCreationException;


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

	public User(String name,
	            String email,
	            String password)
			throws
			DomainResultCreationException {
		if (this.validate(name, email, password)) {
			throw new DomainResultCreationException("validation error");
		}
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public boolean validate(String name,
	                        String email,
	                        String password) {
		return this.validateNameSpecificRules(name) && this.validateEmailSpecificRules(email) &&
		       this.validatePasswordSpecificRules(password);
	}

	private boolean validateNameSpecificRules(String name) {
		return this.name != null || name.length() <= 50;
	}

	private boolean validateEmailSpecificRules(String email) {
		return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	}

	private boolean validatePasswordSpecificRules(String password) {
		return password != null && password.length() >= 8 && password.length() <= 255;
	}

	protected User() {

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