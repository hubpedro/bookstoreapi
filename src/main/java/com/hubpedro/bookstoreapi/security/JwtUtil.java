package com.hubpedro.bookstoreapi.security;

import com.hubpedro.bookstoreapi.model.Permission;
import com.hubpedro.bookstoreapi.model.Role;
import com.hubpedro.bookstoreapi.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
		claims.put("permissions", user.getRoles().stream().flatMap(role -> role.getPermissions().stream()).map(Permission::getName).collect(Collectors.toList()));

		return Jwts.builder()
				       .setSubject(user.getName())
				       .addClaims(claims)
				       .setIssuedAt(new Date())
				       .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				       .signWith(SignatureAlgorithm.HS512, key)
				       .compact();
	}


	public boolean validateToken(String token, UserDetails userDetails) {
		try {
			Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				       .setSigningKey(key)
				       .build()
				       .parseClaimsJws(token)
				       .getBody();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder()
				       .setSigningKey(key)
				       .build()
				       .parseClaimsJws(token)
				       .getBody();
	}

}
