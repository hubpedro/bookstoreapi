package com.hubpedro.bookstoreapi.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;


	private String password;

	public UserDTO() {
	}

	public UserDTO(
			String name,
			String email) {
		this.name = name;
		this.email = email;
	}

}
