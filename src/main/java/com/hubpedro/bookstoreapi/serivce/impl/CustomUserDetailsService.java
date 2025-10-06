package com.hubpedro.bookstoreapi.serivce.impl;

import com.hubpedro.bookstoreapi.model.User;
import com.hubpedro.bookstoreapi.repository.UserRepositoryJPA;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepositoryJPA userRepository;

	public CustomUserDetailsService(UserRepositoryJPA userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Locates the user based on the username. In the actual implementation, the search
	 * may possibly be case sensitive, or case insensitive depending on how theN
	 * implementation instance is configured. In this case, the <code>UserDetails</code>
	 * object that comes back may have a username that is of a different case than what
	 * was actually requested..
	 *
	 * @param username the username identifying the user whose data is required.
	 * @return a fully populated user record (never <code>null</code>)
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 *                                   GrantedAuthority
	 */
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
