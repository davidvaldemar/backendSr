package com.cuscatlan.backendsr.mobile.api;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.mobile.business.MarvelBusiness;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/characters")
@Api(value = "Marvel(R) Characters API", 
     description = "API consultas de personajes Marvel(R)", tags = {
		"", "" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
		@ApiResponse(code = 500, message = "Error interno del servidor"),
		@ApiResponse(code=401, message = "Usuario no autorizado"),
		@ApiResponse(code = 400, message = "Solicitud con parametros incorrectos") })
@Slf4j
public class MarvelCharactersApi {

	@Autowired
	private MarvelBusiness marvelBusiness;
	
	private String API = "characters"; 
	
	@ApiOperation(value = "Búsqueda por nombre de personaje", notes = "Se realiza busqueda por igualdad, el nombre deberá coincidir con el registrado en Marvel(R)")
	@GetMapping(consumes = {"application/json"}, produces = {"application/json"} )
	public ResponseEntity<String> createAuthenticationToken(@RequestParam(name="name", required = false) String name,
			@RequestParam(name="comics", required = false) String comics,
			@RequestParam(name="series", required = false) String series)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<String> result = null; 
		try {
			String response = marvelBusiness.getCharacterByName(name, comics, series, uuid);
			result = new ResponseEntity<String>(response,HttpStatus.OK);
		}catch(Exception e) {			
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	@ExceptionHandler({ AuthenticationException.class,BadCredentialsException.class, AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
		log.error("{} - ExceptionHandler message: {}", e.getMessage(), HttpStatus.UNAUTHORIZED);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
}
