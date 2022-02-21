package com.cuscatlan.backendsr.mobile.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.lib.dto.characters.Comics;
import com.cuscatlan.backendsr.lib.dto.characters.MarvelCharactersResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterAllImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterByNameResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterComicsListResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse.ImageDescription;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.cuscatlan.backendsr.mobile.services.MarvelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CharactersMarvelBusiness {
	
	@Autowired
	private MarvelService marvelService;
	
	private String DES_OK = "Transacción exitosa";
	private String DESC_404 = "Búsqueda sin resultados";
	
	@Autowired
	private TransactionLogBusiness transactionLogBusiness;
	
	public  CharacterByNameResponse getCharacterByName(String characterName, String comics, String series, String uuid, String api, String username) {
		Result response = null;
		CharacterByNameResponse resultData = new CharacterByNameResponse();
		MarvelCharactersResponse characterData = null;
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(null,characterName, comics, series, null,null, uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					Gson gson = new GsonBuilder().create();
					characterData = gson.fromJson(result.getBody(), MarvelCharactersResponse.class);					
					response = Result.builder().code(result.getStatusCodeValue()).descripcion(DES_OK).uuid(uuid).build(); 
				}else {
					response = Result.builder().code(HttpStatus.NOT_FOUND.value()).descripcion(DESC_404).uuid(uuid).build(); 
				}
			}else {
				throw new Exception("Error consultando servicio");
			}
			
		}catch(Exception e) {
			log.error("{} - Error consultando por nombre de personaje, detalle: {}", uuid, e.getMessage(),e);
			response = Result.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).descripcion(e.getMessage()).uuid(uuid).build(); 
		}
		finally {
		
			resultData.setData(characterData);
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	
		
	public  CharacterComicsListResponse getComicsByCharacter(String characterName, String uuid, String api, String username) {
		CharacterComicsListResponse resultData= new CharacterComicsListResponse();
		Result response = null;
		//String body = null;
		Comics comics = null;
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(characterName,null,null,null, null,null, uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					
					Gson gson = new GsonBuilder().create();
					MarvelCharactersResponse pojo = gson.fromJson(result.getBody(), MarvelCharactersResponse.class);
					
					if(pojo!=null) {
						
						if(pojo.getCode()==200 && pojo.getData()!=null && pojo.getData().getResults()!=null && pojo.getData().getResults().size()>0
								&& pojo.getData().getResults().get(0).getComics()!=null ) {
							comics = pojo.getData().getResults().get(0).getComics();							
							response = Result.builder().code(result.getStatusCodeValue()).descripcion(DES_OK).uuid(uuid).build(); 
							
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
	 

	public  CharacterImageDescriptionResponse getImageAndDescriptionByCharacter(String characterName, String uuid, String api, String username) {
		CharacterImageDescriptionResponse resultData= new CharacterImageDescriptionResponse();
		Result response = null;
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(characterName,null,null,null, null, null,uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					
					Gson gson = new GsonBuilder().create();
					MarvelCharactersResponse pojo = gson.fromJson(result.getBody(), MarvelCharactersResponse.class);
					
					if(pojo!=null) {
						
						if(pojo.getCode()==200 && pojo.getData()!=null && pojo.getData().getResults()!=null && pojo.getData().getResults().size()>0) {
							
							resultData.setData( ImageDescription.builder().name(pojo.getData().getResults().get(0).getName()).description(pojo.getData().getResults().get(0).getDescription()).thumbnail(pojo.getData().getResults().get(0).getThumbnail()).build());
							response = Result.builder().code(result.getStatusCodeValue()).descripcion(DES_OK).uuid(uuid).build(); 
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
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	
	public  CharacterAllImageDescriptionResponse getImageAllCharacters(String limit, String offset, String uuid, String api, String username) {
		CharacterAllImageDescriptionResponse resultData= new CharacterAllImageDescriptionResponse();
		Result response = null;
		List<com.cuscatlan.backendsr.lib.dto.characters.api.CharacterAllImageDescriptionResponse.ImageDescription> data = 
				new ArrayList<com.cuscatlan.backendsr.lib.dto.characters.api.CharacterAllImageDescriptionResponse.ImageDescription>();
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(null,null,null,null,limit,offset,uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					
					Gson gson = new GsonBuilder().create();
					MarvelCharactersResponse pojo = gson.fromJson(result.getBody(), MarvelCharactersResponse.class);
					
					if(pojo!=null) {
						
						if(pojo.getCode()==200 && pojo.getData()!=null && pojo.getData().getResults()!=null && pojo.getData().getResults().size()>0) {
							
							
							for (com.cuscatlan.backendsr.lib.dto.characters.Result current: pojo.getData().getResults()) {
								data.add( com.cuscatlan.backendsr.lib.dto.characters.api.CharacterAllImageDescriptionResponse.ImageDescription.builder().name(current.getName()).description(current.getDescription()).thumbnail(current.getThumbnail()).build());
								
							}
							resultData.setData(data);
							response = Result.builder().code(result.getStatusCodeValue()).descripcion(DES_OK).uuid(uuid).build(); 
						}else {
							response = Result.builder().code(result.getStatusCodeValue()).descripcion(DESC_404).uuid(uuid).build(); 
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
	
}
