package com.nst.da;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class 	DaApplication {
	public static void main(String[] args) {
		SpringApplication.run(DaApplication.class, args);
	}

	public static class SecurityConfig {
	}
}

