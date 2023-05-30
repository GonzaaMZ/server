package com.gonzalo.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	
	private  UserAuthenticationEntryPoint userAuthenticationEntryPoint;
	private  UserAuthenticationProvider userAuthProvider;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http
			.exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
		.and()
		.addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeHttpRequests((requests) -> requests
					.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
					.anyRequest().authenticated()
				);
		return http.build();
	}

}










