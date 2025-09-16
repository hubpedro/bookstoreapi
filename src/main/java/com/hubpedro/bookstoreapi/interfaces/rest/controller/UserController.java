package com.hubpedro.bookstoreapi.interfaces.rest.controller;

import com.hubpedro.bookstoreapi.application.serivce.impl.UserAppService;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserAppService userAppService;

	public UserController(UserAppService userAppService) {
		this.userAppService = userAppService;
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userDTO) {
		UserResponse createdUser = userAppService.createUser(userDTO);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping
	public List<UserResponse> getAllUsers() {
		return userAppService.getAllUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		UserResponse user = userAppService.getUserById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		userAppService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
		UserResponse updatedUser = userAppService.updateUser(id, userRequest);
		return ResponseEntity.ok(updatedUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
		UserResponse patchedUser = userAppService.patchUser(id, userRequest);
		return ResponseEntity.ok(patchedUser);
	}
}