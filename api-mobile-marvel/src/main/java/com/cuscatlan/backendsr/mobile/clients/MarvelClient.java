package com.cuscatlan.backendsr.mobile.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cuscatlan.backendsr.mobile.config.FeignConfig;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;

@FeignClient(name = "middleware-marvel-rest", url = "${marvel.url}", configuration = FeignConfig.class)
public interface MarvelClient {
	
	@GetMapping("${marvel.method.get-character}")
	public ResponseEntity<String> getCharacterByName(
			@RequestParam(name = "name") String characterName,
			@RequestParam(name = "comics") String comics, 
			@RequestParam(name = "series") String series
			);
	
	@GetMapping("${get-comic-by-name}")
	public ResponseEntity<String> getComicByTitle(
			@RequestParam(name = "title") String title 
			);
	
	@GetMapping("${get-characters-by-comic}")
	public ResponseEntity<String> getCharactersByComicId(@PathVariable("comicId") String comicId, 
			@RequestParam(name = "name") String characterName
			);
	
	@GetMapping("${get-characters-by-serie}")
	public ResponseEntity<String> getCharactersBySerie(@PathVariable("seriesId") String comicId, 
			@RequestParam(name = "name") String serieName);
	
	
	@GetMapping("${get-comics-by-character}") 
	public ResponseEntity<String> getComicsByCharacterId(@PathVariable("characterId") String comicId, 
			@RequestParam(name = "name") String characterName);
	
	
	@GetMapping("${get-image-and-desc-by-character}")
	public ResponseEntity<String> getImageandDescByCharacter( 
			@RequestParam(name = "name") String characterName
			);

	@GetMapping("${get-list-by-comic}")
	public ResponseEntity<String> getComicListByCommicName( 
			@RequestParam(name = "name") String characterName
			);
	
	@GetMapping("${get-comic-by-id}")
	public ResponseEntity<String> getComicById(@PathVariable("comicId") String comicId);
	
	@GetMapping("${get-comic-by-creator}")
	public ResponseEntity<String> getComicByCreator( 
			@RequestParam(name = "firstName") String firstName,
			@RequestParam(name = "middleName") String middleName, 
			@RequestParam(name = "lastName") String lastName);
	
	@GetMapping("${get-images-all-characters}")
	public ResponseEntity<String> getImagesAllCharacters(
			@RequestParam("limit") int limit,
			@RequestParam("offset") int offset);
	
	@GetMapping("${post-log}")
	public ResponseEntity<TransactionLog> saveLog(@RequestBody  TransactionLog transactionLog);
	
	
}
