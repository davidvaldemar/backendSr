package com.cuscatlan.backendsr.middleware;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableCaching
@EnableFeignClients
@CrossOrigin(origins = "*")
public class ApiMiddlewareMarvelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMiddlewareMarvelApplication.class, args);
	}
	

	   @PostConstruct
	    public void started() {
	        TimeZone.setDefault(TimeZone.getTimeZone("CST"));
	    }
	

}
