package com.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrasactionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrasactionsApplication.class, args);
	}

}
