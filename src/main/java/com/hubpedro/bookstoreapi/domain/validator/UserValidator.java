package com.hubpedro.bookstoreapi.domain.validator;

public class UserValidator {
	public static String notBlank(String value, String message) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return value.trim();
	}

	public static String maxLength(String value, int max, String message) {
		if (value != null && value.length() > max) {
			throw new IllegalArgumentException(message);
		}
		return value;
	}
}