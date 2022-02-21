package com.cuscatlan.backendsr.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableEurekaClient
@SpringBootApplication
@EnableCaching
@EnableSwagger2
@EnableFeignClients
@CrossOrigin(origins = "*")
public class ApiMobileMarvelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMobileMarvelApplication.class, args);
	}

}
