package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.config.ApiPaths;
import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.USERS)
public class UserController {

	private final UserService userService;
	private final UserMapper  userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper  = userMapper;
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userDTO) throws Exception {

		User createdUser = this.userService.createUser(userDTO);
		return new ResponseEntity<UserResponse>(userMapper.toResponse(createdUser), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<User>> getPaginatedUsers(
			@RequestParam(defaultValue = "") String author,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size

	) {
		Page<User> users = this.userService.listPaginated(author, page, size);
		return ResponseEntity.accepted().body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		User         user         = this.userService.getUserById(id);
		UserResponse userResponse = this.userMapper.toResponse(user);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		this.userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
		UserResponse updatedUser = this.userService.updateUser(id, userRequest);
		return ResponseEntity.ok(updatedUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
		UserResponse patchedUser = this.userService.patchUser(id, userRequest);
		return ResponseEntity.ok(patchedUser);
	}
}