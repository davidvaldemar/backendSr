package com.cuscatlan.backendsr.middleware.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cuscatlan.backendsr.middleware.api.entities.TransactionLog;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

	public List<TransactionLog> findByUsernameAndApiOrderByCreatedDateDesc(String user, String api);
	
	public List<TransactionLog> findByUsernameOrderByCreatedDateDesc(String user);
	
	public List<TransactionLog> findByApiAndCreatedDateBetweenOrderByUsernameAsc(String api, Date createdDate, Date createdDate1);
}
