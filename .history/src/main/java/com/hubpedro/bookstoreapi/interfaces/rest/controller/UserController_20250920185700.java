package com.hubpedro.bookstoreapi.interfaces.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hubpedro.bookstoreapi.application.serivce.impl.UserService;
import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import com.hubpedro.bookstoreapi.interfaces.mapper.UserMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userDTO) {
        UserResponse createdUser = this.userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = this.userService.getUserById(id);
        UserResponse userResponse = this.userMapper.toResponse(user);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
        } else {
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