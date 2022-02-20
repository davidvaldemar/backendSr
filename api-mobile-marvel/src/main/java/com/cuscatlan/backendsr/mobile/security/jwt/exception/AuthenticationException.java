package com.cuscatlan.backendsr.mobile.security.jwt.exception;

public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 8248773558889427663L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}
