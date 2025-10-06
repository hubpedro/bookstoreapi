package com.hubpedro.bookstoreapi.mapper;

import com.hubpedro.bookstoreapi.dto.UserRequest;
import com.hubpedro.bookstoreapi.dto.UserResponse;
import com.hubpedro.bookstoreapi.exceptions.DomainValidateException;
import com.hubpedro.bookstoreapi.model.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserMapper {

	public User toUser(UserRequest userRequest) throws DomainValidateException {
		User user = User.create(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword());
		user.setRoles(Set.of("user"));
		return user;
	}

	public UserResponse toResponse(User user) {
		return UserResponse.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).build();
	}
}