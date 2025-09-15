package com.hubpedro.bookstoreapi.infra.persistance.repository;

import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

	private final UserRepositoryJPA userRepositoryJPA;

	public UserRepositoryImpl(UserRepositoryJPA userRepositoryJPA) {
		this.userRepositoryJPA = userRepositoryJPA;
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepositoryJPA.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userRepositoryJPA.findAll();
	}

	@Override
	public User save(User user) {
		return userRepositoryJPA.save(user);
	}

	@Override
	public void deleteById(Long id) {
		userRepositoryJPA.deleteById(id);
	}

}
