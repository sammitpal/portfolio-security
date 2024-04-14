package com.security.dto;

import org.springframework.stereotype.Component;


@Component
public class TokenResponse {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
