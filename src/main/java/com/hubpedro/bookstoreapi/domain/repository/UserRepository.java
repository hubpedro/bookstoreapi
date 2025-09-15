package com.hubpedro.bookstoreapi.domain.repository;

import com.hubpedro.bookstoreapi.domain.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
	Optional<User> findById(Long id);

	List<User> findAll();

	User save(User user);

	void deleteById(Long id);

//	Optional<User> findByEmail(String email);
}