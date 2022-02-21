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
@RequestMapping("/comics")
@Slf4j
public class ComicApi {
	
	@Autowired
	private MarvelBusiness marvelBusiness;
	
	/*
	 * Consulta el comic por nombre y tambien para obtener la lista de comic por titulo
	 */
	@GetMapping()
	public ResponseEntity<?> getComicByTitle(@RequestParam(name = "title") String title){
		String uuid= Utilities.getUuid();
		ResponseEntity<?> result = null;
		try {
			
			result = marvelBusiness.getComicByTitle(title, uuid);
			
		}catch(Exception e) {
			
			log.error("Error {}", e.getMessage(),e);
			result = new ResponseEntity<>("Error consultando API Marvel",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	
	@GetMapping("{comicId}/characters")
	public ResponseEntity<?> getCharactersByComicId(@PathVariable("comicId") String comicId,@RequestParam(name = "name") String characterName){
		String uuid= Utilities.getUuid();
		ResponseEntity<?> result = null;
		try {
			
			result = marvelBusiness.getCharactersByComicId(comicId,characterName,uuid);
		}catch(Exception e) {
			
			log.error("Error {}", e.getMessage(),e);
			result = new ResponseEntity<>("Error consultando API Marvel",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	@GetMapping("{comicId}")
	public ResponseEntity<?> getComicById(@PathVariable("comicId") String comicId){
		String uuid= Utilities.getUuid();
		ResponseEntity<?> result = null;
		try {
			
			result = marvelBusiness.getComicById(comicId,uuid);
		}catch(Exception e) {			
			log.error("Error {}", e.getMessage(),e);
			result = new ResponseEntity<>("Error consultando API Marvel",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	

}
