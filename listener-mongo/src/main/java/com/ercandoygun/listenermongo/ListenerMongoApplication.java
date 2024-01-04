package com.ercandoygun.listenermongo;

import com.ercandoygun.listenermongo.dao.RandomDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.ercandoygun.listenermongo.dao")
@SpringBootApplication
public class ListenerMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListenerMongoApplication.class, args);
    }

}
