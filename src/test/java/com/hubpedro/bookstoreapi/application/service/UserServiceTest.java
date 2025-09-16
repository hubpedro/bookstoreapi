//package com.hubpedro.bookstoreapi.application.service;
//
//
//import com.hubpedro.bookstoreapi.application.serivce.impl.UserServiceImpl;
//import com.hubpedro.bookstoreapi.domain.model.User;
//import com.hubpedro.bookstoreapi.infra.persistance.repository.UserRepository;
//import com.hubpedro.bookstoreapi.application.mapper.UserMapper;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
// public class UserServiceTest
//{
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserMapper userMapper;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private User user;
//    private UserDTO userDTO;
//    private List<User> userList;
//    private Long testId;
//
//    @BeforeEach
//    void setUp()
//{
//        // Setup test data
//        testId = 1L;
//
//        user = new User();
//        // Set id directly on the entity using reflection or direct field access if needed
//        // For test purposes, we'll mock the responses instead of relying on actual entity getter methods
//
//        userDTO = new UserDTO();
//        userDTO.setId(testId);
//
//        userList = new ArrayList<>();
//        userList.add(user);
//    }
//
//    @Test
//    void testSave()
//{
//        // Arrange
//        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
//
//        // Act
//        UserDTO result = userService.save(userDTO);
//
//        // Assert
//        assertThat(result).isNotNull();
//        verify(userRepository).save(any(User.class));
//        verify(userMapper).toDto(any(User.class));
//    }
//
//    @Test
//    void testFindAll()
//{
//        // Arrange
//        List<UserDTO> dtoList = new ArrayList<>();
//        dtoList.add(userDTO);
//
//        when(userRepository.findAll()).thenReturn(userList);
//
//        // Stub the individual entity mapping instead of the list mapping
//        // This correctly handles the stream().map() call in the service
//        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
//
//        // Act
//        List<UserDTO> result = userService.findAll();
//
//        // Assert
//        assertThat(result).isNotNull().hasSize(1);
//        verify(userRepository).findAll();
//    }
//
//    @Test
//    void testFindOne()
//{
//        // Arrange
//        when(userRepository.findById(any())).thenReturn(Optional.of(user));
//        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);
//
//        // Act
//        UserDTO result = userService.findOne(testId);
//
//        // Assert
//        assertThat(result).isNotNull();
//        verify(userRepository).findById(any());
//    }
//
//    @Test
//    void testDelete()
//{
//        // Arrange
//        doNothing().when(userRepository).deleteById(any());
//
//        // Act
//        userService.delete(testId);
//
//        // Assert
//        verify(userRepository).deleteById(any());
//    }
//}
