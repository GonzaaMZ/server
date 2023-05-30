package com.gonzalo.login.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private  UserAuthenticationProvider userAuthProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String headerString = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(headerString != null) {
			String[] elements = headerString.split(" ");
			
			if(elements.length == 2 && "Bearer".equals(elements[0])) {
				try {
					SecurityContextHolder.getContext().setAuthentication(
								userAuthProvider.validateToken(elements[1])
							);
				} catch (RuntimeException e) {
					// TODO: handle exception
					SecurityContextHolder.clearContext();
					throw e;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
