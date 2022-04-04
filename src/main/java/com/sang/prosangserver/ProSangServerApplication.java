package com.sang.prosangserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProSangServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProSangServerApplication.class, args);
	}

}
