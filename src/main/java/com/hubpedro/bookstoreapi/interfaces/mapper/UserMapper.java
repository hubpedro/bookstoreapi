package com.hubpedro.bookstoreapi.interfaces.mapper;


import com.hubpedro.bookstoreapi.domain.model.User;
import com.hubpedro.bookstoreapi.interfaces.dto.UserRequest;
import com.hubpedro.bookstoreapi.interfaces.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

	public User toUser(UserRequest userRequest) {
		return User.create(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
	}

	public UserResponse toResponse(User user) {
		return UserResponse.builder()
				.name(user.getName())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();
	}

	public User toUser(UserResponse userResponse) {
		return User.create(userResponse.getName(), userResponse.getEmail(), userResponse.getPassword());
	}

}
