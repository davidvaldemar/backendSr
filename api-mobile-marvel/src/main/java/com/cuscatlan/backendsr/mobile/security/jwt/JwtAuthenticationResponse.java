package com.cuscatlan.backendsr.mobile.security.jwt;

import java.io.Serializable;
import java.util.HashMap;

public class JwtAuthenticationResponse implements Serializable {

	private static final long serialVersionUID = 1250166508152483573L;

	private HashMap<String, String> token = new HashMap<>();

	public HashMap<String, String> getToken() {
		return this.token;
	}

	public JwtAuthenticationResponse(String token) {
		// this.token = token;

		this.token.put("token", token);
		this.token.put("refreshToken", token);
		// return map;
	}
}
