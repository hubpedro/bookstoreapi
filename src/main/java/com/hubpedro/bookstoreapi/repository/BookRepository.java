package com.hubpedro.bookstoreapi.repository;

import com.hubpedro.bookstoreapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	@Query(
            value = "(SELECT * FROM book WHERE title = :title)",
			nativeQuery = true
	)
    boolean findByTitle(@Param("title") String title);

}