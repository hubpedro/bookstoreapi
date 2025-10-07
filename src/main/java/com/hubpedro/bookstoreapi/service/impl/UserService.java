package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.exceptions.UserNotFoundException;
import com.hubpedro.bookstoreapi.exceptions.UsernameAlreadyExistsException;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	private final UserMapper        userMapper;

	private final UserRepositoryJPA userRepository;

	private final PasswordEncoder passwordEncoder;

	private final JwtUtil jwtUtil;

	public UserService(UserMapper userMapper, UserRepositoryJPA userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {

		this.userRepository = userRepository;
		this.userMapper     = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	public Page<User> listPaginated(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return userRepository.findByName(name, pageable);
	}

	@Transactional
	public User createUser(UserRequest userRequest) throws DomainValidateException {
		User user = userMapper.toUser(userRequest);


		boolean nameAlreadyExists = userRepository.findByName(user.getName()).isPresent();
		boolean emailAlreadyExists = userRepository.findByEmail(user.getEmail()).isPresent();

		if (nameAlreadyExists || emailAlreadyExists) {
			throw new UsernameAlreadyExistsException("Username or email already exists");
		}


		user.setPassword(passwordEncoder.encode((user.getPassword())));
		String token = jwtUtil.generateToken(user);
		System.out.println(token);

		return userRepository.save(user);
	}

	public List<UserResponse> getAllUsers() {

		return userRepository.findAll().stream().map(userMapper::toResponse).toList();
	}

	public User getUserById(Long id) {

		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user with this id was not found"));
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