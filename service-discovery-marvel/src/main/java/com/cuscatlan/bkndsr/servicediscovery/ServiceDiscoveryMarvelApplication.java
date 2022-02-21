package com.cuscatlan.bkndsr.servicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryMarvelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryMarvelApplication.class, args);
	}

}
