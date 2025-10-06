package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.serivce.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private final CustomUserDetailsService userDetailsService;

	public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((authz) -> authz
						                                  .requestMatchers(HttpMethod.POST, ApiPaths.USERS).permitAll()
						                                  .requestMatchers("/api/books/**").hasRole("USER") // Ou a role necessária
						                                  .anyRequest().authenticated()
				).httpBasic(Customizer.withDefaults());
		return http.build();
	}


	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http,
	                                                   PasswordEncoder passwordEncoder,
	                                                   CustomUserDetailsService userDetailsService) throws Exception {
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		return builder.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
