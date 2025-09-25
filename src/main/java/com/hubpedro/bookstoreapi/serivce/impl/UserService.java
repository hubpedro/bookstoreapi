package com.hubpedro.bookstoreapi.serivce.impl;


import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.exceptions.UserEmailAlreadyExists;
import com.hubpedro.bookstoreapi.exceptions.UsernameAlreadyExistsException;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserMapper        userMapper;
	private final UserRepositoryJPA userRepository;

	public UserService(UserMapper userMapper, UserRepositoryJPA userRepository) {
		this.userRepository = userRepository;
		this.userMapper     = userMapper;
	}

	@Transactional()
	public UserResponse createUser(UserRequest userRequest) {
		User user = userMapper.toUser(userRequest);


		if (userRepository.findByName(user.getName()).isPresent()) {
			throw new UsernameAlreadyExistsException(user.getName());
		}

		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserEmailAlreadyExists(user.getEmail());
		}

		User savedUser = userRepository.save(user);

		return userMapper.toResponse(savedUser);
	}

	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
		                     .map(userMapper::toResponse)
		                     .toList();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public UserResponse updateUser(Long id, UserRequest userRequest) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.updateFrom(userMapper.toUser(userRequest));
		User updatedUser = userRepository.save(user);
		return userMapper.toResponse(updatedUser);
	}

	public UserResponse patchUser(Long id, UserRequest userRequest) {
		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		if (userRequest.getName() != null) {
			user.setName(userRequest.getName());
		}
		if (userRequest.getEmail() != null) {
			user.setEmail(userRequest.getEmail());
		}
		if (userRequest.getPassword() != null) {
			user.setPassword(userRequest.getPassword());
		}
		User patchedUser = userRepository.save(user);
		return userMapper.toResponse(patchedUser);
	}

}
