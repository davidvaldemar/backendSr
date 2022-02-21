package com.cuscatlan.backendsr.mobile.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.lib.util.dto.Result;
import com.cuscatlan.backendsr.mobile.entities.TransactionLog;
import com.cuscatlan.backendsr.mobile.services.MarvelService;

@Service
public class TransactionLogBusiness {
	
	@Autowired
	private MarvelService marvelService;
	
	public void saveLog( String api, String method, Result result, String username) {
		 TransactionLog request = new TransactionLog();
		 request.setApi(api);
		 request.setApiMethod(method);
		 request.setResponseCode(result.getCode()+"");
		 request.setResponseDescription(result.getDescripcion());
		 request.setUuid(result.getUuid());
		 request.setCreatedDate(new Date());
		 request.setUsername(username);
		marvelService.saveLog(request );
	 }
	
	public List<TransactionLog> getLogsListByUsernameCriteria(String username, String criteria){		
		
		return marvelService.getLogsListByUsernameCriteria(username, criteria).getBody();
		
	}
	
	public List<TransactionLog> getLogsListByUsernameCriteriaDates(String criteria, String startDate, String endDate ){		
		
		return marvelService.getLogsListByUsernameCriteriaDates( criteria, startDate, endDate).getBody();
		
	}
	
	 
}
