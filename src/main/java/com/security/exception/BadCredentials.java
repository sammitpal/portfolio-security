package com.security.exception;


public class BadCredentials extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadCredentials(String message) {
		super(message);
	}

}
