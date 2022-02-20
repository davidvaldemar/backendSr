package com.cuscatlan.backendsr.mobile.entities;

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
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name= "username")
	private String username;
	@Column(nullable = false, name= "password")
	private String password;
	
	@Column(nullable = false, name= "status")
	private boolean status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "created_date")
	private Date createdDate;
	
}
