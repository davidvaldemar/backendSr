package com.cuscatlan.backendsr.mobile.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.lib.dto.characters.Comics;
import com.cuscatlan.backendsr.lib.dto.characters.MarvelCharactersResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterComicsListResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse.ImageDescription;
import com.cuscatlan.backendsr.lib.dto.comics.MarvelComicsResponse;
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsIdResponse;
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsListByTitleResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.cuscatlan.backendsr.mobile.services.MarvelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ComicsMarvelBusiness {
	
	@Autowired
	private MarvelService marvelService;
	
	private String DES_OK = "Transacción exitosa";
	private String DESC_404 = "Búsqueda sin resultados";
	
	@Autowired
	private TransactionLogBusiness transactionLogBusiness;
	
	public ComicsListByTitleResponse getComicsListByTitle(String title, String uuid, String api, String username) {
		Result response = null;
		MarvelComicsResponse comicsData = null;
		ComicsListByTitleResponse resultData = new ComicsListByTitleResponse();
		try {
			ResponseEntity<String> result = marvelService.getComicByTitle(title, uuid);
			
			if(result!=null) {
				
				if(result.getStatusCodeValue()==200){
					Gson gson = new GsonBuilder().create();
					comicsData = gson.fromJson(result.getBody(), MarvelComicsResponse.class);	
					if(comicsData!=null && comicsData.getCode()==200 && comicsData.getData()!=null && comicsData.getData().getResults()!=null) {

						response = Result.builder().code(comicsData.getCode()).descripcion(DES_OK).uuid(uuid).build(); 
						
					}else {
						response = Result.builder().code(HttpStatus.NOT_FOUND.value()).descripcion(DESC_404).uuid(uuid).build(); 
					}
				}else {
					response = Result.builder().code(result.getStatusCodeValue()).descripcion(DESC_404).uuid(uuid).build(); 
				}
			}else {
				throw new Exception("Error consultando servicio");
			}
			
		}catch(Exception e) {
			log.error("{} - Error consultando por nombre de personaje, detalle: {}", uuid, e.getMessage(),e);
			response = Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).descripcion(e.getMessage()).uuid(uuid).build(); 
		}
		finally {
		
			resultData.setData(comicsData);
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	
	/*
	public  CharacterImageDescriptionResponse getImageAndDescriptionByCharacter(String characterName, String uuid, String api, String username) {
		CharacterImageDescriptionResponse resultData= new CharacterImageDescriptionResponse();
		Result response = null;
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(characterName,null,null,null, uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					
					Gson gson = new GsonBuilder().create();
					MarvelCharactersResponse pojo = gson.fromJson(result.getBody(), MarvelCharactersResponse.class);
					
					if(pojo!=null) {
						
						if(pojo.getCode()==200 && pojo.getData()!=null && pojo.getData().getResults()!=null && pojo.getData().getResults().size()>0) {
							
							resultData.setData( ImageDescription.builder().description(pojo.getData().getResults().get(0).getDescription()).thumbnail(pojo.getData().getResults().get(0).getThumbnail()).build());
							response = Result.builder().code(pojo.getCode()).descripcion(DES_OK).uuid(uuid).build(); 
						}else {
							response = Result.builder().code(pojo.getCode()).descripcion(DESC_404).uuid(uuid).build(); 
						}
						
					}else {
						response = Result.builder().code(HttpStatus.NOT_FOUND.value()).descripcion(DESC_404).uuid(uuid).build();  
					}
				}else {
					response = Result.builder().code(result.getStatusCodeValue()).descripcion(DESC_404).uuid(uuid).build(); 
				}
			}else {
				throw new Exception("Error consultando servicio");
			}
			
		}catch(Exception e) {
			log.error("{} - Error consultando por nombre de personaje, detalle: {}", uuid, e.getMessage(),e);
			response = Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).descripcion(e.getMessage()).uuid(uuid).build(); 
		}
		finally {		
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	*/
	
	public  ComicsIdResponse getComicsById(String comicId, String uuid, String api, String username) {
		ComicsIdResponse resultData= new ComicsIdResponse();
		Result response = null;
		MarvelComicsResponse comics = null;
		try {
			ResponseEntity<String> result = marvelService.getComicById(comicId, uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					
					Gson gson = new GsonBuilder().create();
					MarvelComicsResponse pojo = gson.fromJson(result.getBody(), MarvelComicsResponse.class);
					
					if(pojo!=null) {
						
						if(pojo.getCode()==200 && pojo.getData()!=null && pojo.getData().getResults()!=null && pojo.getData().getResults().size()>0
								&& pojo.getData().getResults()!=null ) {
							comics = pojo;							
							response = Result.builder().code(pojo.getCode()).descripcion(DES_OK).uuid(uuid).build(); 
							
						}else {
							response = Result.builder().code(HttpStatus.NOT_FOUND.value()).descripcion(DESC_404).uuid(uuid).build(); 
						}
						
					}else {
						response = Result.builder().code(HttpStatus.NOT_FOUND.value()).descripcion(DESC_404).uuid(uuid).build();  
					}
				}else {
					response = Result.builder().code(result.getStatusCodeValue()).descripcion(DESC_404).uuid(uuid).build(); 
				}
			}else {
				throw new Exception("Error consultando servicio");
			}
			
		}catch(Exception e) {
			log.error("{} - Error consultando por nombre de personaje, detalle: {}", uuid, e.getMessage(),e);
			response = Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).descripcion(e.getMessage()).uuid(uuid).build(); 
		}
		finally {		
			resultData.setData(comics);;
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	 
	
}
