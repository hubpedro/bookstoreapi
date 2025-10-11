package com.hubpedro.bookstoreapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Permission {
	@Id
	private String name;
	
	private String description;

	public Permission() {
	}

	public Permission(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
