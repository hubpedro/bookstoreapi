package com.hubpedro.bookstoreapi.interfaces.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubpedro.bookstoreapi.application.serivce.impl.UserAppService;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAppService userAppService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequest validUserRequest;
    private UserResponse validUserResponse;

    @BeforeEach
    void setUp() {
        validUserRequest = new UserRequest();
        validUserRequest.setName("John Doe");
        validUserRequest.setEmail("john@example.com");
        validUserRequest.setPassword("password123");

        validUserResponse = UserResponse.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .password("password123")
                .build();
    }

    @Test
    void testCreateUser_Success() throws Exception {
        // Given
        Mockito.when(userAppService.createUser(any(UserRequest.class))).thenReturn(validUserResponse);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));

        Mockito.verify(userAppService).createUser(any(UserRequest.class));
    }

    @Test
    void testCreateUser_ValidationError() throws Exception {
        // Given
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setName("Jo"); // Too short
        invalidRequest.setEmail("invalid-email"); // Invalid email
        invalidRequest.setPassword("123"); // Too short

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllUsers_Success() throws Exception {
        // Given
        List<UserResponse> users = Arrays.asList(validUserResponse);
        Mockito.when(userAppService.getAllUsers()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("John Doe"));

        Mockito.verify(userAppService).getAllUsers();
    }

    @Test
    void testGetAllUsers_EmptyList() throws Exception {
        // Given
        Mockito.when(userAppService.getAllUsers()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        Mockito.verify(userAppService).getAllUsers();
    }

    @Test
    void testGetUserById_Success() throws Exception {
        // Given
        Mockito.when(userAppService.getUserById(1L)).thenReturn(validUserResponse);

        // When & Then
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        Mockito.verify(userAppService).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Given
        Mockito.when(userAppService.getUserById(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());

        Mockito.verify(userAppService).getUserById(999L);
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        // Given
        UserRequest updateRequest = new UserRequest();
        updateRequest.setName("Jane Doe");
        updateRequest.setEmail("jane@example.com");
        updateRequest.setPassword("newpassword123");

        UserResponse updatedResponse = UserResponse.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane@example.com")
                .password("newpassword123")
                .build();

        Mockito.when(userAppService.updateUser(eq(1L), any(UserRequest.class))).thenReturn(updatedResponse);

        // When & Then
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));

        Mockito.verify(userAppService).updateUser(eq(1L), any(UserRequest.class));
    }

    @Test
    void testUpdateUser_ValidationError() throws Exception {
        // Given
        UserRequest invalidRequest = new UserRequest();
        invalidRequest.setName("Jo"); // Too short
        invalidRequest.setEmail("invalid-email"); // Invalid email
        invalidRequest.setPassword("123"); // Too short

        // When & Then
        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPatchUser_Success() throws Exception {
        // Given
        UserRequest patchRequest = new UserRequest();
        patchRequest.setName("Jane Doe"); // Only update name

        UserResponse patchedResponse = UserResponse.builder()
                .id(1L)
                .name("Jane Doe")
                .email("john@example.com") // Original email
                .password("password123") // Original password
                .build();

        Mockito.when(userAppService.patchUser(eq(1L), any(UserRequest.class))).thenReturn(patchedResponse);

        // When & Then
        mockMvc.perform(patch("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));

        Mockito.verify(userAppService).patchUser(eq(1L), any(UserRequest.class));
    }

    @Test
    void testPatchUser_PartialUpdate() throws Exception {
        // Given
        UserRequest patchRequest = new UserRequest();
        patchRequest.setEmail("newemail@example.com"); // Only update email

        UserResponse patchedResponse = UserResponse.builder()
                .id(1L)
                .name("John Doe") // Original name
                .email("newemail@example.com") // Updated email
                .password("password123") // Original password
                .build();

        Mockito.when(userAppService.patchUser(eq(1L), any(UserRequest.class))).thenReturn(patchedResponse);

        // When & Then
        mockMvc.perform(patch("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));

        Mockito.verify(userAppService).patchUser(eq(1L), any(UserRequest.class));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(userAppService).deleteUser(1L);
    }

    @Test
    void testDeleteUser_InvalidId() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/users/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetUserById_InvalidId() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/users/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateUser_InvalidId() throws Exception {
        // Given
        UserRequest updateRequest = new UserRequest();
        updateRequest.setName("Jane Doe");
        updateRequest.setEmail("jane@example.com");
        updateRequest.setPassword("newpassword123");

        // When & Then
        mockMvc.perform(put("/api/users/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testPatchUser_InvalidId() throws Exception {
        // Given
        UserRequest patchRequest = new UserRequest();
        patchRequest.setName("Jane Doe");

        // When & Then
        mockMvc.perform(patch("/api/users/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isBadRequest());
    }
}
