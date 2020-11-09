package com.bretgibson.spotifystats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = SecurityAutoConfiguration.class)
public class SpotifystatsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifystatsApplication.class, args);
	}

}
