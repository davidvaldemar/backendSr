package com.cuscatlan.backendsr.mobile.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterAllImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterByNameResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterComicsListResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse;
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

@RefreshScope
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
	
	
	private String getUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		return userDetails.getUsername();
	}
	
	@ApiOperation(value = "Búsqueda por nombre de personaje", notes = "Se realiza búsqueda por inicio del nombre del personaje registrado en Marvel(R), ejemplo: \"Iron \"")
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
			CharacterByNameResponse response = marvelBusiness.getCharacterByName(name, comics, series, uuid, API, this.getUsername());
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
	public ResponseEntity<CharacterComicsListResponse> getComicListByCharacters(			
			@ApiParam(value = "name", required = true, example = "Iron Man") 
				@RequestParam(name="name", required = true) String name)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<CharacterComicsListResponse> result = null; 
		try {			
			CharacterComicsListResponse response = marvelBusiness.getComicsByCharacter(name, uuid, API, this.getUsername());
			result = new ResponseEntity<CharacterComicsListResponse>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	@ApiOperation(value = "Obtener imagen y descripcion de un personaje", notes = "Se realiza busqueda por igualdad en el nombre del personaje registrado en Marvel(R)")
	@GetMapping(value="/image", produces = {"application/json"} )
	public ResponseEntity<CharacterImageDescriptionResponse> getImageAndDescriptionByCharacter(			
			@ApiParam(value = "name", required = true, example = "Iron Man") 
				@RequestParam(name="name", required = true) String name)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<CharacterImageDescriptionResponse> result = null; 
		try {
			
			CharacterImageDescriptionResponse response = marvelBusiness.getImageAndDescriptionByCharacter(name, uuid, API, this.getUsername());
			result = new ResponseEntity<CharacterImageDescriptionResponse>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}

	@ApiOperation(value = "Obtener imagen de personajes con paginación", notes = "Permite obtener las imagenes de todos los personajes registrados en Marvel(R) con paginación")
	@GetMapping(value="/images-all", produces = {"application/json"} )
	public ResponseEntity<CharacterAllImageDescriptionResponse> getImageAllCharacters(			
			@ApiParam(value = "limit", required = true, example = "5") 
				@RequestParam(name="limit", required = true) String limit, 
			@ApiParam(value = "offset", required = true, example = "5") 
				@RequestParam(name="offset", required = true) String offset)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<CharacterAllImageDescriptionResponse> result = null; 
		try {
			
			CharacterAllImageDescriptionResponse response = marvelBusiness.getImageAllCharacters(limit, offset,uuid, API, this.getUsername());
			result = new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}

}
