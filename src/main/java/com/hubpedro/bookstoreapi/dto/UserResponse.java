package com.hubpedro.bookstoreapi.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long   id;
	private String name;
	private String email;
}