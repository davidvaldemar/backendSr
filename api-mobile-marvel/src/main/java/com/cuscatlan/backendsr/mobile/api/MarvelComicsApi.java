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
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterComicsListResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.comics.MarvelComicsResponse;
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsIdResponse;
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsListByTitleResponse;
import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.mobile.business.ComicsMarvelBusiness;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/comics")
@Api(value = "Marvel(R) Comics API", 
     description = "API consultas de historietas Marvel(R)", tags = {
		"", "" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
		@ApiResponse(code = 500, message = "Error interno del servidor"),
		@ApiResponse(code=401, message = "Usuario no autorizado"),
		@ApiResponse(code = 400, message = "Solicitud con parametros incorrectos") })
@Slf4j
public class MarvelComicsApi {

	@Autowired
	private ComicsMarvelBusiness marvelBusiness;
	
	private String API = "comics"; 
	
	
	private String getUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		return userDetails.getUsername();
	}
	
	@ApiOperation(value = "Búsqueda por nombre de historieta", notes = "Se realiza búsqueda por inicio del nombre de la historieta registrada en Marvel(R), ejemplo: \"Adam: Legend \"")
	@GetMapping(produces = {"application/json"} )
	public ResponseEntity<ComicsListByTitleResponse> getComicsListByTitle(			
			@ApiParam(value = "title", required = true, example = "Aero (2019)") 
				@RequestParam(name="title", required = true) String title
			)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<ComicsListByTitleResponse> result = null; 
		try {			
			ComicsListByTitleResponse response = marvelBusiness.getComicsListByTitle(title, uuid, API, this.getUsername());
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
	
	@ApiOperation(value = "Información de historieta por Id", notes = "Se realiza busqueda por Id de historieta de Marvel(R)")
	@GetMapping(value="/detail", produces = {"application/json"} )
	public ResponseEntity<ComicsIdResponse> getComicsById(			
			@ApiParam(value = "comicId", required = true, example = "22942") 
				@RequestParam(name="comicId", required = true) String comicId)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<ComicsIdResponse> result = null; 
		try {			
			ComicsIdResponse response = marvelBusiness.getComicsById(comicId, uuid, API, this.getUsername());
			result = new ResponseEntity<>(response,HttpStatus.OK);
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	/*
	@ApiOperation(value = "Obtener imagen y descripcion de un personaje", notes = "Se realiza busqueda por igualdad en el nombre del personaje registrado en Marvel(R)")
	@GetMapping(value="/image-description", produces = {"application/json"} )
	public ResponseEntity<CharacterImageDescriptionResponse> getImageAndDescriptionByCharacter(			
			@ApiParam(value = "name", required = false, example = "Iron Man") 
				@RequestParam(name="name", required = false) String name)
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
	
	*/

}
