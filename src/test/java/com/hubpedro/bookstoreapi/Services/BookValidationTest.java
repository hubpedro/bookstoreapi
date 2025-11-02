//package com.hubpedro.bookstoreapi.Services;
//
//import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
//import com.hubpedro.bookstoreapi.model.Book;
//import java.math.BigDecimal;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//import org.junit.jupiter.params.provider.ValueSource;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//class BookValidationTest {
//
//	private final String authorToLong = "A".repeat(256);
//
//	@ParameterizedTest
//	@ValueSource(strings = {"", "a", "aa", "aaab"})
//	void validateTitle_WhenAnyTitle_ShouldThrowException(String invalidTitle) {
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			String validatedTitle = Book.validateTitle(invalidTitle);
//		});
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"Heisenberg", "Allan Turing", "Jhon Von Neumann"})
//	void validateTitle_WhenAnyTitle_ShouldPass(String validTitle) {
//
//		String validatedTitle = Book.validateTitle(validTitle);
//
//		Assertions.assertNotNull(validatedTitle, "title is null");
//		Assertions.assertInstanceOf(String.class, validatedTitle);
//		assertEquals(validatedTitle, validTitle, "validatedTitle and validTitle is not equal");
//	}
//
//	@ParameterizedTest
//	@ValueSource(ints = {0, -1, -2, -3, -4})
//	void validateStock_WhenAnyNegative_ShouldThrowException(int invalidStock) {
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			Book.validateStock(invalidStock);
//		});
//	}
//
//	@ParameterizedTest
//	@ValueSource(ints = {1, 5, 10, 100, 1000})
//	void validateStock_WhenAnyPositive_ShouldPass(int validStock) {
//
//		int validatedStock = Book.validateStock(validStock);
//		assertEquals(validatedStock, validStock, "validatedStock and validStock is not equal.");
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"-1.00", "-2", "-0.02"})
//	void validatePrice_WhenNegative_ShouldThrowException(BigDecimal invalidPrice) {
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			Book.validatePrice(invalidPrice);
//		});
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"0", "0.0", "0.00"})
//	void validatePrice_WhenZero_ShouldThrowException(BigDecimal invalidPrice) {
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			Book.validatePrice(BigDecimal.ZERO);
//		});
//	}
//
//	@ParameterizedTest
//	@ValueSource(ints = {100, 200, 300, 400, 500, 600})
//	void validatePrice_WhenAnyPositive_ShouldPass(int validPrice) {
//
//		BigDecimal validatedPrice = Book.validatePrice(BigDecimal.valueOf(validPrice));
//
//		assertNotNull(validatedPrice, "validation of price return is null");
//
//		assertEquals(validatedPrice, BigDecimal.valueOf(validPrice), "validatedPrice and price is not equal");
//	}
//
//	@ParameterizedTest
//	@ValueSource(strings = {"Kurt Cobain", "Adolf Hitler", "Ronaldinho Gaucho", "Rafael Boladão"})
//	void validateAuthor_WhenAnyTitle_ShouldPass(String validAuthor) {
//
//		String validateAuthor = Book.validateAuthor(validAuthor);
//
//		assertNotNull(validateAuthor, "validation of author is null");
//		assertEquals(validateAuthor, validAuthor, "validation of author is not equal");
//	}
//
//	@ParameterizedTest
//	@NullAndEmptySource
//	@ValueSource(strings = {"a", "ab", "abc", "abcd"})
//	void validateAuthor_WhenInvalid_ThrowException(String invalidAuthor) {
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			Book.validateAuthor(invalidAuthor);
//		});
//
//		Assertions.assertThrows(DomainValidateException.class, () -> {
//			Book.validateAuthor(this.authorToLong);
//		});
//	}
//
//}