package com.rideshare.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.rideshare.authservice", "com.rideshare.common.security"})
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuthServiceApplication.class);
        app.setWebApplicationType(WebApplicationType.REACTIVE); // Force Reactive Mode
        app.run(args);
    }
}