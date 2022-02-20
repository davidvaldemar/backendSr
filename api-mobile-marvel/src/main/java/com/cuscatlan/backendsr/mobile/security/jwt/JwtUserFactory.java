package com.cuscatlan.backendsr.mobile.security.jwt;

import com.cuscatlan.backendsr.mobile.entities.Users;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	@SuppressWarnings("serial")
	public static JwtUser create(Users user) {
		return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.isStatus(), user.getCreatedDate(),null) {
		};
	}

}
