package com.ercandoygun.randomgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RandomGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RandomGeneratorApplication.class, args);
	}

}
