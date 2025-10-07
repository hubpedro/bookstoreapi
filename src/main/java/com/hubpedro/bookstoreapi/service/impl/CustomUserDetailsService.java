package com.hubpedro.bookstoreapi.service.impl;

import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepositoryJPA userRepository;

	public CustomUserDetailsService(UserRepositoryJPA userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		List<SimpleGrantedAuthority> authorities =
				user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase())).toList();


		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPassword(),
				authorities
		);
	}
}
