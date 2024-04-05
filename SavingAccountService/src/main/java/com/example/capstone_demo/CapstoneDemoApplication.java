package com.example.capstone_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CapstoneDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapstoneDemoApplication.class, args);
	}
}
