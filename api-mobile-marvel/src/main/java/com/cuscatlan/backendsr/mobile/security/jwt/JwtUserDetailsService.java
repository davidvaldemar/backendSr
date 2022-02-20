package com.cuscatlan.backendsr.mobile.security.jwt;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.mobile.entities.Users;
import com.cuscatlan.backendsr.mobile.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Optional<Users> userOptional = userRepository.findByUsernameAndStatus(username,true);
			
			if (userOptional == null) {
				throw new UsernameNotFoundException(String.format("Usuario '%s' no registrado o inactivo", username));
			} else {
				return JwtUserFactory.create(userOptional.get());
			}
		}catch(NoSuchElementException e) {
			throw new UsernameNotFoundException(String.format("Usuario '%s' no registrado o inactivo", username));
		}catch (Exception ex) {
			log.error("Error loadUserByUsername: {}", ex.getMessage());
		}
		return null;
	}
}
