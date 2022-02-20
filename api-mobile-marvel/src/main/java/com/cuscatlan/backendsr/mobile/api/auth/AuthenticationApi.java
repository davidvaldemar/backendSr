package com.cuscatlan.backendsr.mobile.api.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cuscatlan.backendsr.mobile.api.auth.dto.UserSignUp;
import com.cuscatlan.backendsr.mobile.business.AuthenticationBusiness;
import com.cuscatlan.backendsr.mobile.security.jwt.JwtAuthenticationRequest;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping
@Api(value = "Servicio de autenticación", 
     description = "API de autenticacion JWT para usuarios", tags = {
		"", "" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
		@ApiResponse(code = 500, message = "Error interno del servidor"),
		@ApiResponse(code=401, message = "Usuario no autorizado"),
		@ApiResponse(code=409, message = "Usuario previamente registrado"),
		@ApiResponse(code = 400, message = "Solicitud incorrecta") })
@Slf4j
public class AuthenticationApi {

	@Autowired
	private AuthenticationBusiness authenticationBusiness;
	
	@ApiOperation(value = "Generador de Token de autenticación", notes = "Metodo para generar Token de autenticación de usuarios")
	@PostMapping(value = "${authorization.jwt.route.authentication.path}", consumes = {"application/json"}, produces = {"application/json"} )
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		return authenticationBusiness.createAuthenticationToken(authenticationRequest);
	}

	
	@ApiOperation(value = "Registro de Usuario", notes = "Metodo para crear nuevos usuarios")
	@PostMapping(value = "/signup", consumes = {"application/json"}, produces = {"application/json"} )
	public ResponseEntity<?> signup(@RequestBody UserSignUp user) throws NoSuchElementException {
		return authenticationBusiness.userSignUp(user);
	}

	
	@ExceptionHandler({ AuthenticationException.class,BadCredentialsException.class, AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
		log.error("{} - ExceptionHandler message: {}", e.getMessage(), HttpStatus.UNAUTHORIZED);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	
	


}
