package com.cuscatlan.backendsr.mobile.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cuscatlan.backendsr.mobile.config.FeignConfig;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;

@FeignClient(name = "middleware-marvel-rest", url = "${marvel.url}", configuration = FeignConfig.class)
public interface MarvelClient {
	
	@GetMapping("${marvel.method.get-character}")
	public ResponseEntity<String> getCharacterByName(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "nameStartsWith") String nameStartsWith,			
			@RequestParam(name = "comics") String comics, 
			@RequestParam(name = "series") String series,
			@RequestParam(name = "limit") String limit, 
			@RequestParam(name = "offset") String offset
			);
	
	@GetMapping("${marvel.method.get-comic}")
	public ResponseEntity<String> getComicByTitle(
			@RequestParam(name = "title") String title 
			);
	
	@GetMapping("${marvel.method.get-comic-by-id}")
	public ResponseEntity<String> getComicById(@PathVariable("comicId") String comicId);
	
	@GetMapping("${marvel.method.get-comic-by-creator}")
	public ResponseEntity<String> getComicByCreator( 
			@RequestParam(name = "nameStartsWith") String nameStartsWith);
	
	@GetMapping("${marvel.method.get-log}")
	public ResponseEntity<List<TransactionLog>> getLogsListByUsernameCriteria(
			@RequestParam("username") String username,
			@RequestParam("criteria") String  criteria);
	
	
	@GetMapping("${marvel.method.get-log-dates}")
	public ResponseEntity<List<TransactionLog>> getLogsListByUsernameCriteriaDates(
			@RequestParam("criteria") String criteria,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate);
	
	@PostMapping("${marvel.method.post-log}")
	public ResponseEntity<TransactionLog> saveLog(@RequestBody TransactionLog transactionLog);
	
	
}
