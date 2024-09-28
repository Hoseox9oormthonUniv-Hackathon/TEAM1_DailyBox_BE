package com.team1.DailyBox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyBoxApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyBoxApplication.class, args);
	}

}
