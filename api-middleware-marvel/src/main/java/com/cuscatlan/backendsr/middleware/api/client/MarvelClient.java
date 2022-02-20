package com.cuscatlan.backendsr.middleware.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "marvel-rest", url = "${marvel.url}", configuration = FeignConfig.class)
public interface MarvelClient {
	
	@GetMapping("${marvel.method.get-character}")
	public ResponseEntity<String> getCharacterByName(
			@RequestParam(name = "name") String characterName, 
			@RequestParam(name = "comics") String comics,
			@RequestParam(name = "series") String series, 
			@RequestParam(name = "ts") String ts,			
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-comic-by-name}")
	public ResponseEntity<String> getComicByTitle(
			@RequestParam(name = "title") String title, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-characters-by-comic}")
	public ResponseEntity<String> getCharactersByComicId(@PathVariable("comicId") String comicId, 
			@RequestParam(name = "name") String characterName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-characters-by-serie}")
	public ResponseEntity<String> getCharactersBySerie(@PathVariable("seriesId") String comicId, 
			@RequestParam(name = "name") String serieName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	
	@GetMapping("${get-comics-by-character}") 
	public ResponseEntity<String> getComicsByCharacterId(@PathVariable("characterId") String comicId, 
			@RequestParam(name = "name") String characterName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	
	@GetMapping("${get-image-and-desc-by-character}")
	public ResponseEntity<String> getImageandDescByCharacter( 
			@RequestParam(name = "name") String characterName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);

	@GetMapping("${get-list-by-comic}")
	public ResponseEntity<String> getComicListByCommicName( 
			@RequestParam(name = "name") String characterName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-comic-by-id}")
	public ResponseEntity<String> getComicById(@PathVariable("comicId") String comicId, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-comic-by-creator}")
	public ResponseEntity<String> getComicByCreator( 
			@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "middleName") String middleName, 
			@RequestParam(name = "lastName") String lastName, 
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey);
	
	@GetMapping("${get-images-all-characters}")
	public ResponseEntity<String> getImagesAllCharacters(
			@RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String  hash, 
			@RequestParam("apikey") String apiKey,
			@RequestParam("limit") int limit,
			@RequestParam("offset") int offset);
}
