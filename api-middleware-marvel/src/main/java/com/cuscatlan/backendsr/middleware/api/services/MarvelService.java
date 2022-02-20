package com.cuscatlan.backendsr.middleware.api.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.middleware.api.client.MarvelClient;

@Service
public class MarvelService {

	@Autowired
	private MarvelClient marvelClient;

	@Value("${marvel.key.public}")
	private String publicKey;

	@Value("${marvel.key.private}")
	private String privateKey;

	public String getHash(String uuid) {
		return Utilities.hashMd5(uuid+privateKey+publicKey);
	}
	public ResponseEntity<String> getCharacterbyName(String name, String nameStartsWith, String comics, String series, String uuid) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(!StringUtils.isBlank(nameStartsWith)) map.put("nameStartsWith", nameStartsWith);
		if(!StringUtils.isBlank(name)) map.put("name", name);
		if(!StringUtils.isBlank(comics)) map.put("comics",comics );
		if(!StringUtils.isBlank(series)) map.put("series", series);
	
		map.put("ts", uuid);
		map.put("hash", this.getHash(uuid));
		map.put("apikey", publicKey);
		
		ResponseEntity<String> rs = marvelClient.getCharacterByName(map);
		return rs;
		
	}
		
	public ResponseEntity<String> getComicByTitle(String title , String uuid) throws Exception {		
		return marvelClient.getComicByTitle(title, uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getCharactersByComicId(String comicId, String characterName , String uuid){
		return marvelClient.getCharactersByComicId(comicId, characterName, uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getCharactersBySeriesId(String seriesId, String characterName , String uuid){
		return marvelClient.getCharactersBySerie(seriesId, characterName, uuid, this.getHash(uuid), publicKey);
	}	
	// primero validar que tenga personaje para buscar los comic asociados
	public ResponseEntity<String> getComicsByCharacterId(String characterId, String comicName, String uuid ) throws Exception {
		return marvelClient.getComicsByCharacterId(characterId, comicName, uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getImageandDescByCharacter(String characterName, String uuid) throws Exception {
		return marvelClient.getImageandDescByCharacter(characterName,  uuid, this.getHash(uuid), publicKey);
	}
	
	
	public ResponseEntity<String> getComicListByCommicName(String comicName, String uuid) throws Exception {
		return marvelClient.getComicListByCommicName(comicName,  uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getComicById(String comicId, String uuid) throws Exception {
		return marvelClient.getComicById(comicId,  uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getComicByCreator(String firstName, String middleName, String lastName, String uuid) throws Exception {
	
	return marvelClient.getComicByCreator(firstName, middleName, lastName,  uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getImagesAllCharacters(String characterName, String uuid, int limit, int offset) throws Exception {
		return marvelClient.getImagesAllCharacters( uuid, this.getHash(uuid), publicKey, limit, offset);
	}
}
