package com.hubpedro.bookstoreapi.application.serivce.impl;

import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.infra.persistance.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import com.hubpedro.bookstoreapi.interfaces.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAppServiceTest {

    @Mock
    private UserRepositoryJPA userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserAppService userAppService;

    private UserRequest userRequest;
    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john@example.com");
        userRequest.setPassword("password123");

        user = User.create("John Doe", "john@example.com", "password123");
        user.setId(1L);

        userResponse = UserResponse.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .build();
    }

    @Test
    void testCreateUser_Success() {
        // Given
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        UserResponse result = userAppService.createUser(userRequest);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());

        verify(userMapper).toUser(userRequest);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testGetAllUsers_Success() {
        // Given
        List<User> users = Arrays.asList(user);
        
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        List<UserResponse> result = userAppService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userResponse, result.get(0));

        verify(userRepository).findAll();
        verify(userMapper).toResponse(user);
    }

    @Test
    void testGetAllUsers_EmptyList() {
        // Given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<UserResponse> result = userAppService.getAllUsers();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(userRepository).findAll();
        verify(userMapper, never()).toResponse(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(userResponse);

        // When
        UserResponse result = userAppService.getUserById(1L);

        // Then
        assertNotNull(result);
        assertEquals(userResponse, result);

        verify(userRepository).findById(1L);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testGetUserById_NotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        UserResponse result = userAppService.getUserById(999L);

        // Then
        assertNull(result);

        verify(userRepository).findById(999L);
        verify(userMapper, never()).toResponse(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        // Given
        UserRequest updateRequest = new UserRequest();
        updateRequest.setName("Jane Doe");
        updateRequest.setEmail("jane@example.com");
        updateRequest.setPassword("newpassword123");

        User updatedUser = User.create("Jane Doe", "jane@example.com", "newpassword123");
        updatedUser.setId(1L);

        UserResponse updatedResponse = UserResponse.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane@example.com")
                .password("newpassword123")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUser(updateRequest)).thenReturn(updatedUser);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(updatedResponse);

        // When
        UserResponse result = userAppService.updateUser(1L, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());

        verify(userRepository).findById(1L);
        verify(userMapper).toUser(updateRequest);
        verify(user).updateFrom(updatedUser);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testUpdateUser_NotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userAppService.updateUser(999L, userRequest);
        });

        verify(userRepository).findById(999L);
        verify(userMapper, never()).toUser(any(UserRequest.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testPatchUser_Success() {
        // Given
        UserRequest patchRequest = new UserRequest();
        patchRequest.setName("Jane Doe"); // Only update name

        UserResponse patchedResponse = UserResponse.builder()
                .id(1L)
                .name("Jane Doe")
                .email("john@example.com")
                .password("password123")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(patchedResponse);

        // When
        UserResponse result = userAppService.patchUser(1L, patchRequest);

        // Then
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }

    @Test
    void testPatchUser_NotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userAppService.patchUser(999L, userRequest);
        });

        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        // When
        userAppService.deleteUser(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testPatchUser_PartialUpdate() {
        // Given
        UserRequest patchRequest = new UserRequest();
        patchRequest.setEmail("newemail@example.com"); // Only update email

        UserResponse patchedResponse = UserResponse.builder()
                .id(1L)
                .name("John Doe")
                .email("newemail@example.com")
                .password("password123")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(patchedResponse);

        // When
        UserResponse result = userAppService.patchUser(1L, patchRequest);

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("newemail@example.com", result.getEmail());
        assertEquals("password123", result.getPassword());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);
    }
}
