package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MultipleDatabaseCbmApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatabaseCbmApplication.class, args);
	}

}
