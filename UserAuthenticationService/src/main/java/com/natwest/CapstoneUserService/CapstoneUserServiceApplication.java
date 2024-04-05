package com.natwest.CapstoneUserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CapstoneUserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapstoneUserServiceApplication.class, args);
	}
}
