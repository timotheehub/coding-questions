package com.dbs.question1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Question1Application starts the Spring Boot application.
 */
@SpringBootApplication
public class Question1Application implements CommandLineRunner {

	@Autowired
	private OddNumberService oddNumberService;

	public static void main(String[] args) {
		SpringApplication.run(Question1Application.class, args);
	}

	@Override
	public void run(String... args) {
		oddNumberService.getOddNumbers(0, 10).forEach(System.out::println);
	}
}
