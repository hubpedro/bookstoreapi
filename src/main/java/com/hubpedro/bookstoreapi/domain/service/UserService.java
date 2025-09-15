package com.hubpedro.bookstoreapi.domain.service;

import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.domain.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


@Service
public class UserService {


	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

}
