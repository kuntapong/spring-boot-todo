package com.example.todo.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.Todo;
import com.example.todo.service.TodoService;

@RestController
@RequestMapping(value = "/api/v1/todos")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/")
	@ResponseBody
	public List<TodoDTO> findByTitleAndDueDate(
			@RequestParam(required = false, defaultValue= "") String title, 
			@RequestParam(required = false) Long dueDate) {
		LocalDateTime convertedDuedate = (dueDate != null)? LocalDateTime.ofInstant(Instant.ofEpochMilli(dueDate), ZoneId.systemDefault()): null;
		return this.todoService
				.findByTitleAndDueDate(title, convertedDuedate)
				.stream()
				.map(t -> new TodoDTO(t))
				.collect(Collectors.toList());
	}
	
	@PostMapping("/")
	@ResponseBody
	public TodoDTO createTodo(@RequestBody TodoReq todoReq) {
		Todo result = this.todoService.addTodo(
				todoReq.getTitle(), 
				todoReq.getDescription(), 
				LocalDateTime.ofInstant(Instant.ofEpochMilli(todoReq.getDuedate()), ZoneId.systemDefault()));

		return new TodoDTO(result);
	}
	
	@PutMapping("/{id}")
	@ResponseBody
	public TodoDTO updateTodo(@PathVariable String id, @RequestBody TodoReq todoReq) {
		Todo result = this.todoService.updateTodo(
				id,
				todoReq.getTitle(), 
				todoReq.getDescription(), 
				LocalDateTime.ofInstant(Instant.ofEpochMilli(todoReq.getDuedate()), ZoneId.systemDefault()));

		return new TodoDTO(result);
	}
	
	@DeleteMapping("/{id}")
	public void updateTodo(@PathVariable String id) {
		this.todoService.deleteTodo(id);
	}
}
