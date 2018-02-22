package com.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.todo.controller.TodoController;

@SpringBootApplication
public class TodoApplication {
	
	protected static final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	public static void main(String[] args) {
		log.info("========== Start Application ============");
		SpringApplication.run(TodoApplication.class, args);
	}
}
