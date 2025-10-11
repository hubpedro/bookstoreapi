package com.hubpedro.bookstoreapi.security;

public enum PermissionEnum {
	BOOK_READ("Read books"),
	BOOK_WRITE("Create and Update books"),
	USER_READ("Read users"),
	USER_CREATE("Create users");

	private final String description;

	PermissionEnum(String description) {
		this.description = description;
	}


	public String getName() {
		return this.name(); // Retorna "BOOK_READ", "BOOK_WRITE"...
	}

	public String getDescription() {
		return description;
	}
}
