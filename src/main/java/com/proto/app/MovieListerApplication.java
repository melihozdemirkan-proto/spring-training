package com.proto.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class MovieListerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieListerApplication.class, args);
    }

}
