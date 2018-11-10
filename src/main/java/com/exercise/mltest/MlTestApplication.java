package com.exercise.mltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class MlTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlTestApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello azure!";
	}
}
