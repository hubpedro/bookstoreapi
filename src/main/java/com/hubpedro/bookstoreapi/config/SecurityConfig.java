package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.security.JwtAuthenticationFilter;
import com.hubpedro.bookstoreapi.security.JwtUtil;
import com.hubpedro.bookstoreapi.service.impl.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
	                                       JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		return http.csrf(AbstractHttpConfigurer::disable)
				       .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
				       .authorizeHttpRequests(authz -> authz
						                                       .requestMatchers(HttpMethod.POST, ApiPaths.USERS).permitAll()
						                                       .requestMatchers("/api/books/**").hasRole("USER")
						                                       .anyRequest().authenticated()
				       )
				       .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				       .build();
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


	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil,
	                                                       CustomUserDetailsService customUserDetailsService) {
		return new JwtAuthenticationFilter(jwtUtil, customUserDetailsService);
	}
}
