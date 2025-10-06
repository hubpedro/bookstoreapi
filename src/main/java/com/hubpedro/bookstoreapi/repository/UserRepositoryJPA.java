package com.hubpedro.bookstoreapi.repository;


import com.hubpedro.bookstoreapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {

	Page<User> findByName(String author, Pageable pageable);

	Optional<User> findByName(String name);

	Optional<User> findByEmail(String name);
}
