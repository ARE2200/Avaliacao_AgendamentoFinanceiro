package com.avaliacao.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);

		//back:  mvnw.cmd spring-boot:run
		//front: npm run serve
		//database: mysql localhost
	}

}
