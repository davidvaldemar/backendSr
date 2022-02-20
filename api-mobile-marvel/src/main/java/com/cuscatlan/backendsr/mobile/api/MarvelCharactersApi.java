package com.cuscatlan.backendsr.mobile.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterByNameResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.ComicsListByCharacterResponse;
import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.mobile.business.CharactersMarvelBusiness;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
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
	private CharactersMarvelBusiness marvelBusiness;
	
	private String API = "characters"; 
	
	@ApiOperation(value = "Búsqueda por nombre de personaje", notes = "Se realiza búsqueda por inicio del nombre del personaje registrado en Marvel(R), ejemplo: \"Iron \", buscará todas las ocurrencias")
	@GetMapping(produces = {"application/json"} )
	public ResponseEntity<CharacterByNameResponse> getCharacterByName(			
			@ApiParam(value = "name", required = false, example = "Iron Man") 
				@RequestParam(name="name", required = false) String name,
			@ApiParam(value = "comics", required = false, example = "30092", examples= @Example(value={@ExampleProperty(value="Id de historieta, permite valores separados por coma (,)")}))
				@RequestParam(name="comics", required = false) String comics,
			@ApiParam(value = "series", required = false, example = "2984" , examples= @Example(value={@ExampleProperty(value="Id de serie, permite valores separados por coma (,)")}))
				@RequestParam(name="series", required = false) String series)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<CharacterByNameResponse> result = null; 
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
	                .getPrincipal();
			String username = userDetails.getUsername();
			
			CharacterByNameResponse response = marvelBusiness.getCharacterByName(name, comics, series, uuid, API, username);
			result = new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	@ExceptionHandler({ AuthenticationException.class,BadCredentialsException.class, AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
		log.error("{} - ExceptionHandler message: {}", e.getMessage(), HttpStatus.UNAUTHORIZED);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ApiOperation(value = "Listar historietas por nombre de personaje especifico", notes = "Se realiza busqueda por igualdad en el nombre del personaje registrado en Marvel(R)")
	@GetMapping(value="/comics-list", produces = {"application/json"} )
	public ResponseEntity<ComicsListByCharacterResponse> getComicListByCharacters(			
			@ApiParam(value = "name", required = false, example = "Iron Man") 
				@RequestParam(name="name", required = false) String name)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<ComicsListByCharacterResponse> result = null; 
		try {
			
			ComicsListByCharacterResponse response = marvelBusiness.getComicsByCharacter(name, uuid, API, this.getUsername());
			result = new ResponseEntity<ComicsListByCharacterResponse>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	
	private String getUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		return userDetails.getUsername();
	}
}
