package com.cuscatlan.backendsr.middleware.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.middleware.api.business.MarvelBusiness;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/creators")
@Slf4j
public class CreatorsApi {
	
	@Autowired
	private MarvelBusiness marvelBusiness;
	
	/*
	 * Consulta personajes por nombre de comic para luego  obtener los personajes del comic
	 */
	@GetMapping()
	public ResponseEntity<?> getComicByCreator(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "middleName") String middleName, @RequestParam(name = "lastName") String lastName){
		String uuid= Utilities.getUuid();
		ResponseEntity<?> result = null;
		try {
			
			result = marvelBusiness.getComicByCreator(firstName, middleName, lastName, uuid);
		}catch(Exception e) {
			
			log.error("Error {}", e.getMessage(),e);
			result = new ResponseEntity<>("Error consultando API Marvel",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	

}
