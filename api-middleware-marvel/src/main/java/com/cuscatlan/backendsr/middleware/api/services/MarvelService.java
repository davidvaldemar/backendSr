package com.cuscatlan.backendsr.middleware.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	public ResponseEntity<String> getCharacterbyName(String characterName, String comics, String series, String uuid) throws Exception {
		return marvelClient.getCharacterByName(characterName, comics, series, uuid, this.getHash(uuid), publicKey);
		
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
