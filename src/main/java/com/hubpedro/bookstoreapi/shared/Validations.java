package com.hubpedro.bookstoreapi.shared;

import com.hubpedro.bookstoreapi.domain.exceptions.DomainException;

public class Validations {


	private Validations() {}

	public static String notBlank(String value,
	                              String message) {
		if (value == null || value.trim().isEmpty()) {
			throw new DomainException(message);
		}
		return value.trim();
	}

	public static String maxLength(String value,
	                               int max,
	                               String message) {
		if (value != null && value.length() > max) {
			throw new DomainException(message);
		}
		return value;
	}

	public static <T extends Number> T minValue(T value,
	                                            T min,
	                                            String message) {
		if (value != null && value.doubleValue() < min.doubleValue()) {
			throw new DomainException(message);
		}
		return value;
	}

	public static <T> T notNull(T value,
	                            String message) {
		if (value == null) {
			throw new DomainException(message);
		}
		return value;
	}

}
