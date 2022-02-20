package com.cuscatlan.backendsr.mobile.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;
import com.cuscatlan.backendsr.mobile.services.MarvelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarvelBusiness {
	
	@Autowired
	private MarvelService marvelService;
	
	private String DES_OK = "Transacción exitosa";
	private String DESC_404 = "Búsqueda sin resultados";
	
	public  String getCharacterByName(String characterName, String comics, String series, String uuid) {
		String resultJson = null;
		Result response = null;
		String body = null;
		try {
			ResponseEntity<String> result = marvelService.getCharacterByName(characterName, comics, series, uuid);
			
			if(result!=null) {
				if(result.getStatusCodeValue()==200){
					response = Result.builder().code(result.getStatusCodeValue()).descripcion(DES_OK).uuid(uuid).build(); 
					body = result.getBody();
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
			resultJson = Utilities.getJsonResponse(response, body);
		}
		return resultJson;
	}
	
	 @Async
	public void saveLog( String api, String method, Result result) {
		 
		 TransactionLog request = new TransactionLog();
		 request.setApi(api);
		 request.setApiMethod(method);
		 request.setResponseCode(result.getCode()+"");
		 request.setResponseDescription(result.getDescripcion());
		 request.setUuid(result.getUuid());
		 request.setCreatedDate(new Date());
		marvelService.saveLog(request );
	 }
	 
	 

}
