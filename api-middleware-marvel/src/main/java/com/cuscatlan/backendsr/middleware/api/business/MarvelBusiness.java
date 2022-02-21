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
	
	
	public ResponseEntity<String> getCharactersByComicId(String comicId, String characterName, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getCharactersByComicId(comicId, characterName, uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}	
	
	public ResponseEntity<String> getComicsByCharacterId(String characterId, String comicName, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getComicsByCharacterId(characterId, comicName, uuid) ;
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	
	public ResponseEntity<String> getImageandDescByCharacter(String characterName, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService. getImageandDescByCharacter(characterName, uuid);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	
	public ResponseEntity<String> getComicListByCommicName(String comicName, String uuid){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getComicListByCommicName(comicName, uuid);
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
	
	public ResponseEntity<String> getImagesAllCharacters(String characterName, String uuid, int limit, int offset){
		ResponseEntity<String> response = null;
		
		try {
			response = marvelService.getImagesAllCharacters(characterName, uuid, limit, offset);
		}catch(Exception e) {
			log.error("{} - Error consumo de servicio, detalle: {}", uuid,e.getMessage());
		}
		return response;
	}
	
}
