package com.cuscatlan.backendsr.middleware.api.business;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cuscatlan.backendsr.middleware.api.entities.TransactionLog;
import com.cuscatlan.backendsr.middleware.api.repositories.TransactionLogRepository;

@Service
public class ModelBusiness {

	@Autowired
	private TransactionLogRepository reporsitory;
	
	public List<TransactionLog> getLogByUserAndTypeSearch(String user, String api){	
		List<TransactionLog> result  = null;
		if(StringUtils.isNotBlank(api)) {
			result = reporsitory.findByUsernameAndApiOrderByCreatedDateDesc(user,api);
		}else {
			result = reporsitory.findByUsernameOrderByCreatedDateDesc(user);
		}
		return result;
	}
	
	public List<TransactionLog> findByApiAndCreatedDateBetweenOrderByUsernameAndCreatedDateDesc(String api, Date startDate, Date endDate){		
		List<TransactionLog> result = reporsitory.findByApiAndCreatedDateBetweenOrderByUsernameAsc( api,startDate, endDate);
		return result;
	}
	
	
	public TransactionLog saveLog(TransactionLog transactionLog) {
		return reporsitory.save(transactionLog);
	}
	
	
}
