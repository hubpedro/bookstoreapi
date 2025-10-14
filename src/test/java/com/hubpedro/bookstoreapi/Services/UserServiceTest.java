package com.hubpedro.bookstoreapi.Services;

import com.hubpedro.bookstoreapi.config.Roles;
import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.exceptions.UserNotFoundException;
import com.hubpedro.bookstoreapi.mapper.UserMapper;
import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.RoleRepository;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import com.hubpedro.bookstoreapi.security.JwtUtil;
import com.hubpedro.bookstoreapi.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	private UserRepositoryJPA userRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private UserService userService;

	@Test
	void user_Create_ShouldBeSuccessful() throws Exception {

		final String expectedName = "Pedro Barbosa";
		final String expectedEmail = "testuser@gmail.com";
		final String expectedPassword = "SecurePass123!";
		final String hashedPassword = "hashedPass123";
		final Long expectedId = 1L;

		UserRequest validRequest = new UserRequest(expectedName, expectedEmail, expectedPassword);
		User user = User.create(expectedName, expectedEmail, expectedPassword);

		when(userMapper.toUser(validRequest)).thenReturn(user);
		lenient().when(passwordEncoder.encode("SecurePass123!")).thenReturn("hashedPassword");
		when(userRepository.save(user)).thenReturn(user);
		when(roleRepository.findByName(Roles.USER)).thenReturn(Optional.of(new Role(Roles.USER)));

		// Act
		User result = userService.createUser(validRequest);

		assertNotNull(result, "User should not be null");
		verify(userMapper).toUser(validRequest);
		verify(userRepository).save(user);
	}

	@Test
	void user_Create_WhenNamelIsInvalid_ShouldThrowException() {
		// Act & Assert
		assertThrows(DomainValidateException.class, () -> {
			User.create("", "rafael@gmail.com", "123455678");
		});

	}

	@Test
	void user_Create_WhenEmailIsInvalid_ShouldThrowException() {
		// Act & Assert
		assertThrows(DomainValidateException.class, () -> {
			User.create("rafael boladao", "", "123455678");
		});

	}

	@Test
	void user_Create_WhenPasswordIsInvalid_ShouldThrowException() {
		// Act & Assert
		assertThrows(DomainValidateException.class, () -> {
			User.create("rafael boladao", "rafael@gmail.com", "");
		});

	}

	@Test
	void user() {
		// Arrange
		final Long expectedId = 1L;
		User expectedUser = mock(User.class);

		when(userRepository.findById(expectedId)).thenReturn(Optional.of(expectedUser));

		// Act
		final User result = userService.findUserById(expectedId);

		// Assert
		assertNotNull(result);
		assertSame(expectedUser, result); // Verifica se é o mesmo objeto retornado pelo repositório

		verify(userRepository).findById(expectedId);
	}

	@Test
	void userNotExists_ShouldThrowException() {
		// Arrange
		final Long nonExistentId = 999L;
		when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(UserNotFoundException.class, () -> {
			userService.findUserById(nonExistentId);
		});

		verify(userRepository).findById(nonExistentId);
	}

	@Test
	void deleteUserById_WhenUserExists_ShouldUserDelete() {

		Long expectedId = 1L;

		doNothing().when(userRepository).deleteById(expectedId);

		userService.userDelete(expectedId);

		verify(userRepository, times(1)).deleteById(expectedId);
	}

	@Test
	void updateUserById_WhenUserExists_ShouldUpdateUser() {

		// Arrange - Dados consistentes
		Long id = 1L;

		// Dados ORIGINAIS do usuário no banco
		String originalName = "jorge";
		String originalEmail = "jorge@gmail.com";
		String originalPassword = "12345566777";

		// Dados ATUALIZADOS do request
		String updatedName = "jucaaa";
		String updatedEmail = "juca@gmail.com";
		String updatedPassword = "1293182319283";

		// Usuário existente no banco
		User existingUser = User.create(originalName, originalEmail, originalPassword);
		existingUser.setId(id);

		// Request com dados atualizados
		UserRequest userRequest = new UserRequest(updatedName, updatedEmail, updatedPassword);

		// Usuário convertido do request (dados novos)
		User updatedUserData = User.create(updatedName, updatedEmail, updatedPassword);

		// Response esperado
		UserResponse expectedResponse = new UserResponse(id, updatedName, updatedEmail);

		// Mocks
		when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
		when(userMapper.toUser(userRequest)).thenReturn(updatedUserData);
		when(userRepository.save(existingUser)).thenReturn(existingUser);
		when(userMapper.toResponse(existingUser)).thenReturn(expectedResponse);

		// Act
		UserResponse result = userService.userUpdated(id, userRequest);

		// Assert - Verifica o response
		assertNotNull(result);
		assertEquals(updatedName, result.getName());
		assertEquals(updatedEmail, result.getEmail());

		// Verify - Verifica as interações
		verify(userRepository).findById(id);
		verify(userMapper).toUser(userRequest);
		verify(userRepository).save(existingUser);
		verify(userMapper).toResponse(existingUser);

	}

	@Test
	void updateUserById_WhenUserNotExists_ShouldThrowException() {
		// Arrange
		Long nonExistentId = 999L;
		UserRequest userRequest = new UserRequest("Nome", "email@test.com", "password123");

		when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> {
			userService.userUpdated(nonExistentId, userRequest);
		});

		// Verify - Garante que não tentou salvar
		verify(userRepository, never()).save(any(User.class));
		verify(userMapper, never()).toUser(any(UserRequest.class));
	}

}