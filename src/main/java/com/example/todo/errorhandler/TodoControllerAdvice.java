package com.example.todo.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.todo.service.exception.TodoServiceNotfoundException;

@RestControllerAdvice
public class TodoControllerAdvice {
	
	protected static final Logger log = LoggerFactory.getLogger(TodoControllerAdvice.class);
	
	@ExceptionHandler(TodoServiceNotfoundException.class)
	public ResponseEntity<String> notFoundException(final TodoServiceNotfoundException e) {
		log.warn("Error not found({})", e.getMessage());
		
	    return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<String> wrongRequestTypeException(final MethodArgumentTypeMismatchException e) {
		log.warn("Error Invalid request type({})", e.getMessage());
		
	    return new ResponseEntity<String>("The parameter is invalid.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> general(final Exception e) {
		log.error("Error({})", e.getMessage());
		
	    return new ResponseEntity<String>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
