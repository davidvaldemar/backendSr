package com.cuscatlan.backendsr.mobile.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.mobile.clients.MarvelClient;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;

@Service
public class MarvelService {

	@Autowired
	private MarvelClient marvelClient;

	public ResponseEntity<String> getCharacterByName(String name, String nameStartsWith, String comics, String series, String limit, String offset, String uuid) throws Exception {
		return marvelClient.getCharacterByName(name, nameStartsWith, comics, series, limit, offset);		
	}
		
	public ResponseEntity<String> getComicByTitle(String title , String uuid) throws Exception {		
		return marvelClient.getComicByTitle(title );
	}
	
	public ResponseEntity<String> getComicById(String comicId, String uuid) throws Exception {
		return marvelClient.getComicById(comicId);
	}
	
	public ResponseEntity<String> getComicByCreator(String nameStartsWith, String uuid) throws Exception {
	
	return marvelClient.getComicByCreator(nameStartsWith);
	}

	public ResponseEntity<TransactionLog> saveLog(TransactionLog request) {
		return marvelClient.saveLog(request);
	}
	
	public ResponseEntity<List<TransactionLog>> getLogsListByUsernameCriteria(String username, String criteria ) {
		return marvelClient.getLogsListByUsernameCriteria(username, criteria);
	}
	
	public ResponseEntity<List<TransactionLog>> getLogsListByUsernameCriteriaDates(String criteria, String startDate, String endDate) {
		return marvelClient.getLogsListByUsernameCriteriaDates( criteria, startDate, endDate);
	}
	
}
