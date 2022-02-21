package com.cuscatlan.backendsr.mobile.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.lib.dto.characters.MarvelCharactersResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterComicsListResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse;
import com.cuscatlan.backendsr.lib.dto.characters.api.CharacterImageDescriptionResponse.ImageDescription;
import com.cuscatlan.backendsr.lib.dto.comics.MarvelComicsResponse;
import com.cuscatlan.backendsr.lib.dto.comics.api.ComicsListByTitleResponse;
import com.cuscatlan.backendsr.lib.dto.creators.Comics;
import com.cuscatlan.backendsr.lib.dto.creators.MarvelCreatorsResponse;
import com.cuscatlan.backendsr.lib.dto.creators.api.CreatorComicsListResponse;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.cuscatlan.backendsr.mobile.services.MarvelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreatorsMarvelBusiness {
	
	@Autowired
	private MarvelService marvelService;
	
	private String DES_OK = "Transacción exitosa";
	private String DESC_404 = "Búsqueda sin resultados";
	
	@Autowired
	private TransactionLogBusiness transactionLogBusiness;
	
	public CreatorComicsListResponse getComicByCreator(String nameStartsWith, String uuid, String api, String username) {
		Result response = null;
		MarvelCreatorsResponse creatorsData = null;
		CreatorComicsListResponse resultData = new CreatorComicsListResponse();
		try {
			ResponseEntity<String> result = marvelService.getComicByCreator(nameStartsWith, uuid);
			
			if(result!=null) {
				
				if(result.getStatusCodeValue()==200){
					Gson gson = new GsonBuilder().create();
					creatorsData = gson.fromJson(result.getBody(), MarvelCreatorsResponse.class);	
					if(creatorsData.getCode()==200 && creatorsData.getData()!=null && creatorsData.getData().getResults()!=null && creatorsData.getData().getResults().size()>0
								&& creatorsData.getData().getResults().get(0).getComics()!=null ) {
							//comics = creatorsData.getData().getResults().get(0).getComics();							
							response = Result.builder().code(creatorsData.getCode()).descripcion(DES_OK).uuid(uuid).build(); 
							
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
		
			resultData.setData(creatorsData);
			resultData.setResult(response);
			transactionLogBusiness.saveLog(api, Thread.currentThread().getStackTrace()[1].getMethodName(), response, username);
		}
		return resultData;
	}
	
}
