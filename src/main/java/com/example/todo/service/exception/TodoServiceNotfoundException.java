package com.example.todo.service.exception;

public class TodoServiceNotfoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1040794782458540359L;

	public TodoServiceNotfoundException(String id) {
	    super("Cound not found the item ID: " + id);
	}
}
