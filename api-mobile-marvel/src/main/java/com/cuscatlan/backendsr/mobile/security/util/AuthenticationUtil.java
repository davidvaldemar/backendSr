/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cuscatlan.backendsr.mobile.security.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.cuscatlan.backendsr.mobile.security.jwt.JwtAuthenticationRequest;
import com.cuscatlan.backendsr.mobile.security.jwt.JwtAuthenticationResponse;
import com.cuscatlan.backendsr.mobile.security.jwt.JwtTokenUtil;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthenticationUtil{

	@Value("${authorization.jwt.header}")
	private String tokenHeader;
	
	@Value("${authorization.jwt.expiration}")
	private String expiration;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	@Qualifier("jwtUserDetailsService")
	private UserDetailsService userDetailsService;
	

	public ResponseEntity<?> getObjectToken(JwtAuthenticationRequest authenticationRequest)
			throws UsernameNotFoundException, InvalidKeyException, UnsupportedEncodingException {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		HashMap<String, Object> map = new HashMap<>();
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		if (userDetails != null) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			map.put("access_token", token);
			map.put("userName", userDetails.getUsername());
			map.put("type","Bearer");
			map.put("issued_at",Instant.now().toEpochMilli());	
			map.put("expires_in",expiration );
			return ResponseEntity.ok().body(map);
		}

		log.error( "Acceso no autorizado: {}",  HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<?> getRefreshToken(HttpServletRequest request) throws ExpiredJwtException, SignatureException,
			UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, UnsupportedEncodingException {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		if (jwtTokenUtil.canTokenBeRefreshed(token, new Date())) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("Usuaario no autorizado", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Acceso no autorizado, credenciales invalidas", e);
		}
	}

}
