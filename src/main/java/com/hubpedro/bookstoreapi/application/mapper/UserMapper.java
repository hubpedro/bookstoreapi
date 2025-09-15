package com.hubpedro.bookstoreapi.application.mapper;


import com.hubpedro.bookstoreapi.application.dto.UserDTO;
import com.hubpedro.bookstoreapi.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserDTO toDTO(User user) {
		return new UserDTO(user.getName(), user.getEmail());
	}

	public User toEntity(UserDTO userDTO) {
		return new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword());
	}

}
