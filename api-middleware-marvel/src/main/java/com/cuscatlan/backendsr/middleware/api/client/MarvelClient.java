package com.cuscatlan.backendsr.middleware.api.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import feign.QueryMap;

@FeignClient(name = "marvel-rest", url = "${marvel.url}", configuration = FeignConfig.class)
public interface MarvelClient {

	@GetMapping("${marvel.method.get-character}")
	public ResponseEntity<String> getCharacterByName(@SpringQueryMap Map<String, Object> map );

	@GetMapping("${marvel.method.get-comic}")
	public ResponseEntity<String> getComicByTitle(@RequestParam(name = "titleStartsWith") String titleStartsWith, @RequestParam(name = "ts") String ts,
			@RequestParam(name = "hash") String hash, @RequestParam("apikey") String apiKey);
	@GetMapping("${marvel.method.get-comic-by-id}")
	public ResponseEntity<String> getComicById(@PathVariable("comicId") String comicId,
			@RequestParam(name = "ts") String ts, @RequestParam(name = "hash") String hash,
			@RequestParam("apikey") String apiKey);

	@GetMapping("${marvel.method.get-comic-by-creator}")
	public ResponseEntity<String> getComicByCreator(@RequestParam(name = "nameStartsWith") String nameStartsWith,			
			@RequestParam(name = "ts") String ts, @RequestParam(name = "hash") String hash,
			@RequestParam("apikey") String apiKey);

}
