package com.rale.bonkerlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BonkerListApplication {

	public static void main(String[] args) {

		SpringApplication.run(BonkerListApplication.class, args);
	}


}
