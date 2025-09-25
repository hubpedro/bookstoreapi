package com.hubpedro.bookstoreapi.interfaces.rest.controller;

import com.hubpedro.bookstoreapi.application.serivce.impl.UserService;
import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import com.hubpedro.bookstoreapi.interfaces.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserResponse createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserResponse userResponse = userMapper.toResponse(user);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse patchedUser = userService.patchUser(id, userRequest);
        return ResponseEntity.ok(patchedUser);
    }
}