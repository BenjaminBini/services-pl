package com.sully.covid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Application bootstrap class
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StationMapApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationMapApplication.class, args);
    }

}
