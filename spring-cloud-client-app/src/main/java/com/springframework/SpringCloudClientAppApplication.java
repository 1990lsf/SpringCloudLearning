package com.springframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCloudClientAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudClientAppApplication.class, args);
	}


}
