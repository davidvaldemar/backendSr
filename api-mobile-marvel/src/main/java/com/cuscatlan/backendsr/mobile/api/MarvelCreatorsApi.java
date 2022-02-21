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

import com.cuscatlan.backendsr.lib.dto.creators.api.CreatorComicsListResponse;
import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.mobile.business.CreatorsMarvelBusiness;
import com.cuscatlan.backendsr.mobile.security.jwt.exception.AuthenticationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/creators")
@Api(value = "Marvel(R) Creators API", 
     description = "API consultas de creatodes (autores) de historietas Marvel(R)", tags = {
		"", "" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
		@ApiResponse(code = 500, message = "Error interno del servidor"),
		@ApiResponse(code=401, message = "Usuario no autorizado"),
		@ApiResponse(code = 400, message = "Solicitud con parametros incorrectos") })
@Slf4j
public class MarvelCreatorsApi {

	@Autowired
	private CreatorsMarvelBusiness marvelBusiness;
	
	private String API = "creators"; 
	
	
	private String getUsername() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		return userDetails.getUsername();
	}
	
	@ApiOperation(value = "Búsqueda de historietas por nombre de creador", notes = "Se realiza búsqueda por inicio del nombre del creador de la historieta registrada en Marvel(R), ejemplo: \"St. L \"")
	@GetMapping(produces = {"application/json"} )
	public ResponseEntity<CreatorComicsListResponse> getComicByCreator(			
			@ApiParam(value = "nameStartsWith", required = true, example = "Stan L") 
				@RequestParam(name="nameStartsWith", required = true) String nameStartsWith
			)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<CreatorComicsListResponse> result = null; 
		try {
			CreatorComicsListResponse response = marvelBusiness.getComicByCreator(nameStartsWith, uuid, API, this.getUsername());
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

}
