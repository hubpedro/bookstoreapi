package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.config.ProtectedEndPoints;
import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProtectedEndPoints.USERS)
public class UserController {

	private final UserService userService;
	private final UserMapper  userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper  = userMapper;
	}

	@GetMapping
    public ResponseEntity<Pageable> getPaginatedUsers(
			@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size,
            @RequestParam(defaultValue = "id") String sort

	) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.status(HttpStatus.OK).body(pageable);
	}

	@GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = this.userService.findUserById(id);
        if (userResponse != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        this.userService.userDelete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = this.userService.userUpdated(id, userRequest);
		return ResponseEntity.ok(updatedUser);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse patchedUser = this.userService.userPatch(id, userRequest);
		return ResponseEntity.ok(patchedUser);
	}
}