package com.cuscatlan.backendsr.middleware.api.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.middleware.api.services.MarvelService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarvelBusiness {
	
	@Autowired
	private MarvelService marvelService;

	public ResponseEntity<String> getCharacterByName(String name,  String nameStartsWith,String comics, String series,String limit, String offset,  String uuid) throws Exception {
		ResponseEntity<String> response = null;
		try {	
			response = marvelService.getCharacterbyName(name, nameStartsWith, comics, series, limit, offset,uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
			
	}
	
	public ResponseEntity<String> getComicByTitle(String title, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getComicByTitle(title, uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	
	
	
	public ResponseEntity<String> getComicById(String comicId, String uuid) {
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getComicById(comicId, uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	
	public ResponseEntity<String> getComicByCreator(String nameStartsWith, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getComicByCreator(nameStartsWith, uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	

	
}
