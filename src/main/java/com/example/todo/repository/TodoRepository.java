package com.example.todo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.todo.domain.Todo;

public interface TodoRepository extends MongoRepository<Todo, String> {
	List<Todo> findByTitleLikeAndDueDateOrderByDueDateDesc(String title, LocalDateTime duedate);
	List<Todo> findByTitleLikeOrderByDueDateDesc(String title);
	
	Optional<Todo> findById(String id);
}
