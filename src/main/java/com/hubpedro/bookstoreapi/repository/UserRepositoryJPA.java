package com.hubpedro.bookstoreapi.repository;


import com.hubpedro.bookstoreapi.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJPA extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);

	Optional<User> findByEmail(String name);
}
