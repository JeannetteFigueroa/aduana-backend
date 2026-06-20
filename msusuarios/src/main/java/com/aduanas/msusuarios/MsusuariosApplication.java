package com.aduanas.msusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsusuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsusuariosApplication.class, args);
    }

}
