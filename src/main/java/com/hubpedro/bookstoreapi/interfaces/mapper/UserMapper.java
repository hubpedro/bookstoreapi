package com.hubpedro.bookstoreapi.interfaces.mapper;


import com.hubpedro.bookstoreapi.application.dto.UserData;
import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public UserData toData(UserRequest userRequest) {
		return UserData.builder()
		               .name(userRequest.getName())
		               .email(userRequest.getEmail())
		               .password(userRequest.getPassword())
		               .build();
	}

	public UserResponse toResponse(UserData userData) {
		return UserResponse.builder()
				.name(userData.getName())
				.email(userData.getEmail())
				.password(userData.getPassword()).build();
	}

	public User toUser(UserData userData) {
		return new User(userData.getName(),userData.getEmail(),userData.getPassword());
	}

}
