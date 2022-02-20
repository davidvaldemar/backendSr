package com.cuscatlan.backendsr.mobile.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
	 
}
