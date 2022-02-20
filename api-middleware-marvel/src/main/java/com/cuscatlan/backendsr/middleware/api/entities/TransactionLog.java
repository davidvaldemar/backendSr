package com.cuscatlan.backendsr.middleware.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "transaction_log")
public class TransactionLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(nullable = false, name= "api_method")
	private String apiMethod;
	
	@Column(nullable = true, name= "response_code")
	private String responseCode;
	
	@Column(nullable = true, name= "response_description")
	private String responseDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_date")
	private Date createdDate;
	
	@Column(nullable = false, name= "uuid")
	private String uuid;
	
	@Column(nullable = false, name= "api")
	private String api;
	
	@Column(nullable = false, name= "username")
	private String username;
}
