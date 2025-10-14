package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.config.Roles;
import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.exceptions.UserNotFoundException;
import com.hubpedro.bookstoreapi.exceptions.UsernameAlreadyExistsException;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.RoleRepository;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.security.JwtUtil;
import com.hubpedro.bookstoreapi.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService implements IUserService {

	private final UserMapper        userMapper;

	private final UserRepositoryJPA userRepository;

	private final PasswordEncoder passwordEncoder;


	private final RoleRepository roleRepository;

	public UserService(RoleRepository roleRepository, UserMapper userMapper, UserRepositoryJPA userRepository,
	                   PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.userMapper     = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

    public Page<UserResponse> listPaginatedUser(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userMapper::toResponse);
	}

	@Transactional(rollbackFor = {DomainValidateException.class, UsernameAlreadyExistsException.class})
    public UserResponse createUser(UserRequest userRequest) throws DomainValidateException {
		User user = userMapper.toUser(userRequest);
		boolean nameAlreadyExists = userRepository.findByName(user.getName()).isPresent();
		boolean emailAlreadyExists = userRepository.findByEmail(user.getEmail()).isPresent();

		if (nameAlreadyExists || emailAlreadyExists) {
			throw new UsernameAlreadyExistsException("Username or email already exists");
		}

		Role userRole = roleRepository.findByName(Roles.USER)
				                .orElseThrow(() -> new RuntimeException("Default role not found: ROLE_USER"));


		user.setPassword(passwordEncoder.encode((user.getPassword())));
		user.setRoles(Collections.singleton(userRole));
        User newUser = userRepository.save(user);

        return userMapper.toResponse(newUser);
	}

    public UserResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                                  .orElseThrow(() -> new UserNotFoundException("user with this id was not found"));
        return userMapper.toResponse(user);
	}

    public void userDelete(Long id) {

		userRepository.deleteById(id);
	}

    public UserResponse userUpdated(Long id, UserRequest userRequest) {

		User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
		user.updateFrom(userMapper.toUser(userRequest));
		User updatedUser = userRepository.save(user);
		return userMapper.toResponse(updatedUser);
	}

    public UserResponse userPatch(Long id, UserRequest userRequest) {

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