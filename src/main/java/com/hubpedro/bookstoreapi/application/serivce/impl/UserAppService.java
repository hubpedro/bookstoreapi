package com.hubpedro.bookstoreapi.application.serivce.impl;


import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.infra.persistance.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import com.hubpedro.bookstoreapi.interfaces.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserAppService {

	private final UserMapper userMapper;
	private final UserRepositoryJPA userRepository;
	
	public UserAppService(UserMapper userMapper, UserRepositoryJPA userRepository) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public UserResponse createUser(UserRequest userRequest) {
		User user = userMapper.toUser(userRequest);
		User savedUser = userRepository.save(user);
		return userMapper.toResponse(savedUser);
	}

	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
		                  .map(userMapper::toResponse)
		                  .collect(Collectors.toList());
	}

	public UserResponse getUserById(Long id) {
		return userRepository.findById(id)
		                  .map(userMapper::toResponse)
		                  .orElse(null);
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
