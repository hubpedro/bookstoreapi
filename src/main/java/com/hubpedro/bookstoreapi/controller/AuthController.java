package com.hubpedro.bookstoreapi.controller;

import com.hubpedro.bookstoreapi.config.PublicEndPoints;
import com.hubpedro.bookstoreapi.dto.AuthResponse;
import com.hubpedro.bookstoreapi.dto.LoginRequest;
import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.security.JwtUtil;
import com.hubpedro.bookstoreapi.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(PublicEndPoints.AUTH)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepositoryJPA userRepositoryJPA;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserRepositoryJPA userRepositoryJPA,
                          UserService userService,
                          UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepositoryJPA = userRepositoryJPA;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
        UserResponse newUser = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .header("Location", "/api/users/" + newUser.getId())
                             .header("X-Request-ID", UUID.randomUUID().toString())
                             .header("X-API-Version", "1.0")
                             // 🎯 O único easter egg permitido:
                             .header("X-API-Mood", "ProfessionalButFriendly")
                             .body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword())

                                                                          );
        User user = (User) authentication.getPrincipal();  // já implementa UserDetails
        String token = jwtUtil.generateToken(user);

        AuthResponse response = new AuthResponse(token, user.getName(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

        return ResponseEntity.ok(response);
    }
}
