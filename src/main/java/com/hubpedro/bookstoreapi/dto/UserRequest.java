package com.hubpedro.bookstoreapi.dto;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * @param name
 * @param email
 * @param password
 */
public class UserRequest implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String name;

	private String email;

	private String password;

	@Override
	public String toString() {

		return String.format("UserRequest[name={%s} email={%s} requestAt={%s}]", this.name, this.email);
	}

}