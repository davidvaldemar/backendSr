package com.cuscatlan.backendsr.middleware.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	public ResponseEntity<List<TransactionLog>> getLogByUserandCriteria(@RequestParam("user") String user, @RequestParam("criteria") String criteria){
		ResponseEntity<List<TransactionLog>> response =null;
		String uuid = Utilities.getUuid();
		try {
			log.info("{} - Request param: {} , {}", uuid, user, criteria);
			List<TransactionLog> result = modelBusiness.getLogByUserAndTypeSearch(user, criteria);
		
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
	
	
	public ResponseEntity<List<TransactionLog>> getLogByUserAndCriteriaAndDatePeriod(
			@RequestParam("user") String user, 
			@RequestParam("criteria") String criteria,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate
			){
		ResponseEntity<List<TransactionLog>> response =null;
		String uuid = Utilities.getUuid();
		try {
			log.info("{} - Request param: {} , {}, {} , {}", uuid, user, criteria, startDate, endDate);
			List<TransactionLog> result = modelBusiness.getLogByUserAndSearchAndPeriodDate(user, criteria,
					DateUtil.formatStringToDate(startDate, FORMAT_DATE), DateUtil.formatStringToDate(endDate, FORMAT_DATE));
		
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
	
}
