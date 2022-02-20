package com.cuscatlan.backendsr.mobile.api.business;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.mobile.entities.Users;
import com.cuscatlan.backendsr.mobile.repositories.UserRepository;
import com.cuscatlan.backendsr.mobile.security.jwt.JwtAuthenticationRequest;
import com.cuscatlan.backendsr.mobile.security.util.AuthenticationUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationBusiness {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationUtil authenticationUtil;
	
	public ResponseEntity<?> createAuthenticationToken(JwtAuthenticationRequest authenticationRequest)
			throws UsernameNotFoundException, InvalidKeyException, UnsupportedEncodingException, NoSuchElementException {
                  
		  Optional<Users> cliente = userRepository.findByUsernameAndStatus(authenticationRequest.getUsername(), true);
		try {
			if (cliente==null) {
				log.error("AuthenticationBusiness - createAuthenticationToken - usuario {}  no registrado", authenticationRequest.getUsername() );
			}else {
				ResponseEntity<?> response = authenticationUtil.getObjectToken(authenticationRequest);	
				return response;
			}
		}catch(NoSuchElementException e) {
			log.error("AuthenticationBusiness - createAuthenticationToken - usuario no registrado" );
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
	
	public ResponseEntity<Users> userSignUp(com.cuscatlan.backendsr.mobile.api.auth.dto.UserSignUp usersinSignUp) {
		ResponseEntity<Users> result = null;
		Users entity = new Users();
		entity.setUsername(usersinSignUp.getUsername());
		entity.setPassword(usersinSignUp.getPassword());
		entity.setStatus(true);
		entity.setCreatedDate(new Date());
		try{
			Users userResult = userRepository.save(entity);
			result = new ResponseEntity<>(userResult, HttpStatus.OK);
		}catch (Exception e) {
			result = new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
		
		return result;
	}
}
