package com.example.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.exception.TodoServiceNotfoundException;

@Service
public class TodoService {
	@Autowired
	private TodoRepository todoRepository;
	
	public List<Todo> findByTitleAndDueDate(String title, LocalDateTime dueDate) {
		if(dueDate != null) {
			return this.todoRepository.findByTitleLikeAndDueDateOrderByDueDateAsc(title, dueDate);
		}else {
			return this.todoRepository.findByTitleLikeOrderByDueDateAsc(title);
		}
	}
	
	public Todo addTodo(String title, String description, LocalDateTime duedate) {
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setDescription(description);
		todo.setDueDate(duedate);
		todo.setCreatedAt(LocalDateTime.now());
		todo.setUpdatedAt(LocalDateTime.now());
		
		return this.todoRepository.save(todo);
	}
	
	public Todo updateTodo(String id, String title, String description, LocalDateTime duedate) {
		Optional<Todo> result = this.todoRepository.findById(id);
		
		if(result.isPresent()) {
			Todo todo = result.get();
			todo.setTitle(title);
			todo.setDescription(description);
			todo.setDueDate(duedate);
			todo.setUpdatedAt(LocalDateTime.now());
			
			return this.todoRepository.save(todo);
		}else {
			throw new TodoServiceNotfoundException(id);
		}
	}
	
	public void deleteTodo(String id) {
		Optional<Todo> result = this.todoRepository.findById(id);
		
		if(result.isPresent()) {
			this.todoRepository.delete(id);
		}else {
			throw new TodoServiceNotfoundException(id);
		}
	}
}
