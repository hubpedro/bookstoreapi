package com.hubpedro.bookstoreapi.repository;

import com.hubpedro.bookstoreapi.model.Book;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByTitle(@NotBlank(message = "Title is mandatory") String title);

	@Query(
			value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM book WHERE title = :title) THEN true ELSE false END",
			nativeQuery = true
	)
	boolean existsByTitle(@Param("title") String title);

}