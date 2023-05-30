package com.gonzalo.login.config;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gonzalo.login.dto.UserDto;
import com.gonzalo.login.services.UserService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

	@Value("${security.jwt.token.signature:secret-key}")
	private String signature;
	
	private UserService userService;
	
	@PostConstruct
	protected void init() {
		signature = Base64.getEncoder().encodeToString(signature.getBytes());
	}
	
	public String createToken(String login) {
		Date now = new Date();
		Date validityDate = new Date(now.getDate() + 3_600_000);
		
		return JWT.create()
				.withIssuer(login)
				.withIssuedAt(now)
				.withExpiresAt(validityDate)
				.sign(Algorithm.HMAC256(signature));
	}
	
	public Authentication validateToken(String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(signature))
				.build();
		
		DecodedJWT decoded = verifier.verify(token);
		
		UserDto user = userService.findByLogin(decoded.getIssuer());
		
		return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
	}
}







