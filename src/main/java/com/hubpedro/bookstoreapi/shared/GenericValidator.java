package com.hubpedro.bookstoreapi.shared;

import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GenericValidator<T> {
	private final T value;
	private final List<String> errors = new ArrayList<>();


	private GenericValidator(T value) {
		this.value = value;
	}

	public static <T> GenericValidator<T> of(T value) {
		return new GenericValidator<>(value);
	}

	public GenericValidator<T> notNull(String message) {
		if (this.value == null) {
			errors.add(message);
		}
		return this;
	}

	public  GenericValidator<T> notBlank(String message) {
		if (value == null) {
			errors.add(message);
		} else if (value.toString().trim().isEmpty()) {
			errors.add(message);
		}
		return this;
	}
	public GenericValidator<T> validate(Predicate<T> condition, String message) {
		if (value != null && !condition.test(value)) errors.add(message);
		return this;
	}

	public T get() {
		if (!errors.isEmpty()) {
			throw new ValidationException((Throwable) errors);
		}
		return value;
	}

}
