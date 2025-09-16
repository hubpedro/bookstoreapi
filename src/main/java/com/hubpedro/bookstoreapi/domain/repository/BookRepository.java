package com.hubpedro.bookstoreapi.domain.repository;


import com.hubpedro.bookstoreapi.domain.model.Book;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByTitle(@NotBlank(message = "Title is mandatory") String title);
}
