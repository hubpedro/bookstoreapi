package com.hubpedro.bookstoreapi.mapper;


import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public User toUser(UserRequest userRequest) {
		return User.create(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
	}

	public UserResponse toResponse(User user) {
		return UserResponse.builder()
		                   .id(user.getId())
		                   .name(user.getName())
		                   .email(user.getEmail())
		                   .password(user.getPassword())
		                   .build();
	}

	public User toUser(UserResponse userResponse) {
		return User.create(userResponse.getName(), userResponse.getEmail(), userResponse.getPassword());
	}

}
