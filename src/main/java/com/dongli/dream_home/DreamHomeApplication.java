package com.dongli.dream_home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DreamHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamHomeApplication.class, args);
	}

}
