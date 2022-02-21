package com.cuscatlan.backendsr.middleware.api.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	public ResponseEntity<String> getCharacterbyName(String name, String nameStartsWith, String comics, String series, String limit, String offset, String uuid) throws Exception {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(!StringUtils.isBlank(nameStartsWith)) map.put("nameStartsWith", nameStartsWith);
		if(!StringUtils.isBlank(name)) map.put("name", name);
		if(!StringUtils.isBlank(comics)) map.put("comics",comics );
		if(!StringUtils.isBlank(series)) map.put("series", series);
		if(!StringUtils.isBlank(limit)) map.put("limit",limit );
		if(!StringUtils.isBlank(offset)) map.put("offset", offset);
	
		map.put("ts", uuid);
		map.put("hash", this.getHash(uuid));
		map.put("apikey", publicKey);
		
		ResponseEntity<String> rs = marvelClient.getCharacterByName(map);
		return rs;
		
	}
		
	public ResponseEntity<String> getComicByTitle(String titleStartsWith , String uuid) throws Exception {	
		
		return marvelClient.getComicByTitle(titleStartsWith, uuid, this.getHash(uuid), publicKey);
	}
	
	
	public ResponseEntity<String> getComicById(String comicId, String uuid) throws Exception {
		return marvelClient.getComicById(comicId,  uuid, this.getHash(uuid), publicKey);
	}
	
	public ResponseEntity<String> getComicByCreator(String nameStartsWith,String uuid) throws Exception {
	return marvelClient.getComicByCreator(nameStartsWith, uuid, this.getHash(uuid), publicKey);
	}
	
}
