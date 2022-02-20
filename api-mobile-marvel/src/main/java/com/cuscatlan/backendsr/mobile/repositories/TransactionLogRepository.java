package com.cuscatlan.backendsr.mobile.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cuscatlan.backendsr.mobile.entities.TransactionLog;

@Repository
public interface TransactionLogRepository extends CrudRepository<TransactionLog, Long> {

	public List<TransactionLog> findByUserAndApiOrderByCreatedDateDesc(String user, String api);
	
	public List<TransactionLog> findByUserAndApiAndCreatedDateBetweenOrderByCreatedDateDesc(String user, String api, Date startDate, Date endDate);
}
