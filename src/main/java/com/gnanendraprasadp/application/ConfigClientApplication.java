package com.gnanendraprasadp.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;


@SpringBootApplication
public class ConfigClientApplication {

    @Value("${user.name}")
    private String name;

    @Value("${user.email}")
    private String email;

    @Value("${user.place}")
    private String place;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @PostConstruct
    public void output(){
        System.out.println("name = " + name);
        System.out.println("email = " + email);
        System.out.println("place = " + place);
    }
}