package com.hubpedro.bookstoreapi.config;

import com.hubpedro.bookstoreapi.security.JwtAuthenticationFilter;
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

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                   .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                   .authorizeHttpRequests(authz -> authz
                           // endpoints públicos
                           .requestMatchers(HttpMethod.POST, PublicEndPoints.LOGIN).permitAll()
                           .requestMatchers(HttpMethod.POST, PublicEndPoints.REGISTER).permitAll()

                           // endpoints protegidos com permissions granulares
                           .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()      // leitura pública
                           .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("BOOK_WRITE")
                           .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAuthority("BOOK_UPDATE")
                           .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority("BOOK_DELETE")
                           .anyRequest().authenticated())
                   .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
