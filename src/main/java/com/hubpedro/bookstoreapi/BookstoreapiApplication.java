package com.hubpedro.bookstoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookstoreapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreapiApplication.class, args);
	}

}
