package com.volvocars.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.volvocars.assignment"})
public class CongestionTaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CongestionTaxApplication.class, args);
    }
}
