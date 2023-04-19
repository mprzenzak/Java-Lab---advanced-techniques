package com.mprzenzak.lab06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Lab06Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab06Application.class, args);
    }

}
