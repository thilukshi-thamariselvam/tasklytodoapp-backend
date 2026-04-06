package com.app2.tasklytodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TasklytodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklytodoApplication.class, args);
	}

}
