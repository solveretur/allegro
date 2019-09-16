package com.brzezinski.allegrocontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class AllegroContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllegroContentApplication.class, args);
    }
}
