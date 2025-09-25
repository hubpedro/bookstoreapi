package com.hubpedro.bookstoreapi.model;

import java.time.LocalDateTime;

public class AuditableEntity {
	private Book book;
	private User user;

	private LocalDateTime created_by;
	private LocalDateTime updated_by;
}
