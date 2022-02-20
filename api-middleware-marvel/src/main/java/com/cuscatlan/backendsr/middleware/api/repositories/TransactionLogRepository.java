package com.cuscatlan.backendsr.middleware.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cuscatlan.backendsr.middleware.api.entities.TransactionLog;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {

	public List<TransactionLog> findByUsernameAndApiOrderByCreatedDateDesc(String user, String api);
	
	public List<TransactionLog> findByUsernameAndApiAndCreatedDateBetweenOrderByCreatedDateDesc(String user, String api, Date startDate, Date endDate);
}
