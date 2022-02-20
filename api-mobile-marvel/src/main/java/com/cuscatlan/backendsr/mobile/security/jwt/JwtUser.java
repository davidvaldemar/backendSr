package com.cuscatlan.backendsr.mobile.security.jwt;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1215456216545L;

	private Long id;

	private String username;
	private String password;

	private Boolean status;

	private Date createdDate;
	private final Collection<? extends GrantedAuthority> authorities;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true; 
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true; 
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true; 
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public JwtUser(Long id, String username, String password, Boolean status, Date createdDate,
		 Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
		this.createdDate = createdDate;
		this.authorities = authorities;
	}

}
