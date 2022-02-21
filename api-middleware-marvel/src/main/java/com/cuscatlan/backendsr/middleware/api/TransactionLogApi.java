package com.cuscatlan.backendsr.middleware.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cuscatlan.backendsr.lib.util.DateUtil;
import com.cuscatlan.backendsr.lib.util.Utilities;
import com.cuscatlan.backendsr.middleware.api.business.ModelBusiness;
import com.cuscatlan.backendsr.middleware.api.entities.TransactionLog;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/log")
@Slf4j
public class TransactionLogApi {

	@Autowired
	private ModelBusiness modelBusiness;
	
	private final String FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
	
	@GetMapping("")
	public ResponseEntity<List<TransactionLog>> getLogByUserAndCriteria(@RequestParam(name="username", required = true) String username, @RequestParam(name="criteria", required = false) String criteria){
		ResponseEntity<List<TransactionLog>> response =null;
		String uuid = Utilities.getUuid();
		try {
			log.info("{} - Request param: {} , {}", uuid, username, criteria);
			List<TransactionLog> result = modelBusiness.getLogByUserAndTypeSearch(username, criteria);
		
			if(result!=null) {
				response = new ResponseEntity<List<TransactionLog>>(result,HttpStatus.OK);
				log.info("{} - Cantidad de registros recuperados : {}", uuid, result.size());
			}else{
				log.info("{} - No se recuperaron logs ", uuid);
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			log.error("{} - Error consultando logs, {}", uuid, e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/dates")
	public ResponseEntity<List<TransactionLog>> getLogByUserAndCriteriaAndDatePeriod(
			@RequestParam("criteria") String criteria,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate
			){
		ResponseEntity<List<TransactionLog>> response =null;
		String uuid = Utilities.getUuid();
		try {
			log.info("{} - Request param: {} , {} , {}", uuid, criteria, startDate, endDate);
			List<TransactionLog> result = modelBusiness.findByApiAndCreatedDateBetweenOrderByUsernameAndCreatedDateDesc(criteria,
					DateUtil.formatStringToDate(startDate, FORMAT_DATE), DateUtil.formatStringToDate(endDate, FORMAT_DATE));
		
			if(result!=null) {
				response = new ResponseEntity<List<TransactionLog>>(result,HttpStatus.OK);
				log.info("{} - Cantidad de registros recuperados : {}", uuid, result.size());
			}else{
				log.info("{} - No se recuperaron logs ", uuid);
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		}catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			log.error("{} - Error consultando logs, {}", uuid, e.getMessage());
		}
		return response;
	}
	
	@PostMapping(consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<TransactionLog> saveTransactionLog(@RequestBody TransactionLog request){
		ResponseEntity<TransactionLog> response =null;
		String uuid = Utilities.getUuid();
		try {
			
			
			TransactionLog result = modelBusiness.saveLog(request);
			
			if(result!=null) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				log.info("{} - No se escribio log  ", uuid);
				response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
		}catch(Exception e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			log.error("{} - Error consultando logs, {}", uuid, e.getMessage());
		}
		return response;
	}
	
}
