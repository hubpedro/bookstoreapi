package com.hubpedro.bookstoreapi.security;

import com.hubpedro.bookstoreapi.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	public String generateToken(User user) {
		return Jwts.builder()
				       .setSubject(user.getUsername())
				       .claim("id", user.getId())
				       .claim("roles", user.getRoles())
				       .setIssuedAt(new Date())
				       .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
				       .signWith(SignatureAlgorithm.HS512, key)
				       .compact();
	}

	public boolean validateToken(String token) {
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
}
