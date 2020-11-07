package com.company.SWcapstone_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SwCapstoneBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwCapstoneBackendApplication.class, args);
	}

}
