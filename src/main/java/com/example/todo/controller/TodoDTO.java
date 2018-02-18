package com.example.todo.controller;

import java.time.ZoneId;

import com.example.todo.domain.Todo;

public class TodoDTO {
	private String id;
	private String title;
	private String description;
	private Long dueDate;
	private Long createdAt;
	private Long updatedAt;
	
	public TodoDTO(Todo todo) {
		this.id = todo.getId();
		this.title = todo.getTitle();
		this.description = todo.getDescription();
		this.dueDate = todo.getDueDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		this.createdAt = todo.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		this.updatedAt = todo.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getDueDate() {
		return dueDate;
	}
	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
}
