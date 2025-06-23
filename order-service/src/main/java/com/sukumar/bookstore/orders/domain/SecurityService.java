package com.sukumar.bookstore.orders.domain;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	public String getLoginUserName() {
		//Since this service is a resource server Security context holder will have JwtAuthenticationToken as authentication.
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		//return "user";
		Jwt jwt = (Jwt) jwtAuthenticationToken.getPrincipal();
		return jwt.getClaimAsString("preferred_username");
	}
}
