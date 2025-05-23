package com.niam.kardan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.niam")
public class KardanApplication {
    public static void main(String[] args) {
        SpringApplication.run(KardanApplication.class, args);
    }
}