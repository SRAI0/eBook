package com.example.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


    @SpringBootApplication
    @ComponentScan(basePackages = {"com.example.author"})
    public class AuthorApplication {
        public static void main(String[] args) {
            SpringApplication.run(com.example.author.AuthorApplication.class, args);
        }

    }


