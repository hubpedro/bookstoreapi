package com.hubpedro.bookstoreapi.domain.model;


<<<<<<< HEAD
=======
import com.hubpedro.bookstoreapi.domain.exceptions.DomainValidateException;
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD
import org.hibernate.sql.results.DomainResultCreationException;
=======
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885


@Entity(name = "users")
@Getter
@Setter
public class User {
<<<<<<< HEAD
=======

>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Size(min = 5, max = 50)
	private String name;

	@Email
	private String email;

	@Size(min = 8, max = 255)
	private String password;

<<<<<<< HEAD
	public User(String name,
	            String email,
	            String password)
			throws
			DomainResultCreationException {
=======
	private User(String name, String email, String password) throws DomainValidateException {
>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
		if (this.validate(name, email, password)) {
			throw new DomainValidateException("validation error");
		}
		this.name = validateAndSetName(name);
		this.email = validateAndSetEmail(email);
		this.password = validateAndSetPassword(password);
	}

	public static User create(String name, String email, String password) {
		return new User(validateAndSetName(name), validateAndSetEmail(email), validateAndSetPassword(password));
	}

	private static String validateAndSetName(String name) throws DomainValidateException {
		if (name != null && (name.length() > 5 && name.length() <= 50)) {
			return name;
		}
		throw new DomainValidateException("Invalid name");
	}

	private static String validateAndSetEmail(String email) throws DomainValidateException {
		if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
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

<<<<<<< HEAD
=======
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

	public void updateFrom(User user) {
		this.name = validateAndSetName(user.getName());
		this.email = validateAndSetEmail(user.getEmail());
		this.password = validateAndSetPassword(user.getPassword());
	}


>>>>>>> e2cefe11af80af936bf9a3b70a3cdb683f4e0885
	@Override
	public String toString() {
		return "User[" +
		       "id={}" +
		       "name={}" +
		       "email={}" +
		       "password={}";
	}
}