package com.Hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HrmsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmsSystemApplication.class, args);
	}

}
