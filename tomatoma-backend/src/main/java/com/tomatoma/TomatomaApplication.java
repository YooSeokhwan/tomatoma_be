package com.tomatoma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TomatomaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomatomaApplication.class, args);
    }

}
