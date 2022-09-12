package com.ecore.tempo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TempoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempoApplication.class, args);
    }

}
