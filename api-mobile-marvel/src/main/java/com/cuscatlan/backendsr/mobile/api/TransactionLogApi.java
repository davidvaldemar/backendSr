package com.cuscatlan.backendsr.mobile.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.List;
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
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsListByTitleResponse;
import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.mobile.business.ComicsMarvelBusiness;
import com.cuscatlan.backendsr.mobile.business.TransactionLogBusiness;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;
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
@RequestMapping("/log")
@Api(value = "API consulta de transacciones en los APIs", 
     description = "API consultas de logs de transacciones realizadas por los usuario", tags = {
		"", "" })
@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
		@ApiResponse(code = 500, message = "Error interno del servidor"),
		@ApiResponse(code=401, message = "Usuario no autorizado"),
		@ApiResponse(code = 400, message = "Solicitud con parametros incorrectos") })
@Slf4j
public class TransactionLogApi {

	@Autowired
	private TransactionLogBusiness transactionLogBusiness;
	
	@ApiOperation(value = "Búsqueda por nombre de usuario y/o tipo de búsqueda", notes = "Se realiza búsqueda de los de transacciones y devuelve lista de resultados")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Transacción exitosa"),
			@ApiResponse(code = 500, message = "Error interno del servidor"),
			@ApiResponse(code=201, message = "Sin datos")})
	@GetMapping(value="",produces = {"application/json"} )
	public ResponseEntity<List<TransactionLog>> getLogsByUsernameCriteria(			
			@ApiParam(value = "username", required = true, example = "admin") 
				@RequestParam(name="username", required = true) String username,
			@ApiParam(value = "criteria", required = false, example = "characters", allowableValues = "characters,comics,creators") 
				@RequestParam(name="criteria", required = false) String criteria
			)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<List<TransactionLog>> result = null; 
		try {
			
			List<TransactionLog> resultList = transactionLogBusiness.getLogsListByUsernameCriteria(username, criteria);
			if(resultList!=null && resultList.size()>0) {
				result = new ResponseEntity<>(resultList,HttpStatus.OK);
			}else {
				result = new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			log.error("{} - Error {}", uuid,e.getMessage(),e);
			result = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);			
		}			
		return result;
	}
	
	@ApiOperation(value = "Búsqueda por nombre de usuario, tipo de búsqueda y rango de fechas ", notes = "Se realiza búsqueda de los de transacciones y devuelve lista de resultados")
	@GetMapping(value="/dates", produces = {"application/json"} )
	public ResponseEntity<List<TransactionLog>> getLogsListByUsernameCriteriaDates(
			@ApiParam(value = "criteria", required = false, example = "characters",  allowableValues = "characters,comics,creators") 
				@RequestParam(name="criteria", required = false ) String criteria,
			@ApiParam(value = "startDate", required = true, example = "2022-01-01T18:00:00", examples= @Example(value={@ExampleProperty(value="Formato de fecha inicio permitido yyyy-MM-dd'T'HH:mm:ss")})) 
				@RequestParam(name="startDate", required = true) String startDate,
			@ApiParam(value = "endDate", required = true, example = "2022-03-01T08:00:00" , examples= @Example(value={@ExampleProperty(value="Formato de fecha fin permitido yyyy-MM-dd'T'HH:mm:ss")})) 
				@RequestParam(name="endDate", required = true) String endDate
			)
			throws AuthenticationException, UsernameNotFoundException, InvalidKeyException, NoSuchElementException, 
			UnsupportedEncodingException {
		String uuid = Utilities.getUuid();
		ResponseEntity<List<TransactionLog>> result = null; 
		try {
			
			List<TransactionLog> resultList = transactionLogBusiness.getLogsListByUsernameCriteriaDates(criteria,startDate, endDate);
			if(resultList!=null && resultList.size()>0) {
				result = new ResponseEntity<>(resultList,HttpStatus.OK);
			}else {
				result = new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
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
