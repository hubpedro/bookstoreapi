package com.hubpedro.bookstoreapi.dto;


import com.hubpedro.bookstoreapi.shared.GlobalMessages;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 255, updatable = false)
	@NotNull(message = GlobalMessages.NAME_MANDATORY)

	private String name;

	@Email(message = GlobalMessages.EMAIL_MESSAGE)
	@NotNull(message = GlobalMessages.NAME_MANDATORY)
	private String email;

	@Size(min = 8, max = 255, message = "password between 8 and 255 length")
	private String password;
	@Override
	public String toString() {
		return String.format("UserRequest[name={%s} email={%s} requestAt={%s}]", this.name, this.email);
	}
}
