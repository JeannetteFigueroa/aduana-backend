package com.aduanas.msviajeros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsviajerosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsviajerosApplication.class, args);
	}

}
