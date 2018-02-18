package com.example.todo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.exception.TodoServiceNotfoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.example.todo")
@DataMongoTest
public class TodoServiceTest {
	
	private static LocalDateTime dueDate1 = LocalDateTime.now();
	private static LocalDateTime dueDate2 = LocalDateTime.now().plusDays(4);
	@Autowired
	private TodoService todoService;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Before
	public void setUpTest() {
		todoRepository.deleteAll();
		
		Todo todo1 = new Todo();
		todo1.setTitle("test 1 bb");
		todo1.setDescription("test desc 1");
		todo1.setDueDate(dueDate1);
		todo1.setCreatedAt(LocalDateTime.now());
		todo1.setUpdatedAt(LocalDateTime.now());
		
		todoRepository.save(todo1);
		
		Todo todo2 = new Todo();
		todo2.setTitle("test 2 ee");
		todo2.setDescription("test desc 2");
		todo2.setDueDate(dueDate1);
		todo2.setCreatedAt(LocalDateTime.now());
		todo2.setUpdatedAt(LocalDateTime.now());
		
		todoRepository.save(todo2);
		
		Todo todo3 = new Todo();
		todo3.setTitle("test 3 bb");
		todo3.setDescription("test desc 3");
		todo3.setDueDate(dueDate2);
		todo3.setCreatedAt(LocalDateTime.now());
		todo3.setUpdatedAt(LocalDateTime.now());
		
		todoRepository.save(todo3);
	}
	
	@Test
	public void testFindOnlyByTitle() {
		//Find All
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result1.size());
		
		//Specific title
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("bb", null);
		assertEquals(2, result2.size());
		assertEquals("test 1 bb", result2.get(0).getTitle());
		assertEquals("test 3 bb", result2.get(1).getTitle());
		
		List<Todo> result3 = this.todoService.findByTitleAndDueDate("ee", null);
		assertEquals(1, result3.size());
		assertEquals("test 2 ee", result3.get(0).getTitle());
		
		//Not found
		List<Todo> result4 = this.todoService.findByTitleAndDueDate("sdfs", null);
		assertEquals(0, result4.size());
	}
	
	@Test
	public void testFindOnlyByDueDate() {
		//Find All
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result1.size());
		
		//Specific dueDate
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("", dueDate1);
		assertEquals(2, result2.size());
		assertEquals("test 1 bb", result2.get(0).getTitle());
		assertEquals("test 2 ee", result2.get(1).getTitle());
		
		List<Todo> result3 = this.todoService.findByTitleAndDueDate("", dueDate2);
		assertEquals(1, result3.size());
		assertEquals("test 3 bb", result3.get(0).getTitle());
		
		//Not found
		List<Todo> result4 = this.todoService.findByTitleAndDueDate("", LocalDateTime.now());
		assertEquals(0, result4.size());
	}
	
	@Test
	public void testFindByTitleAndDueDate() {
		//Match title all but not duedate
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("test", LocalDateTime.now());
		assertEquals(0, result1.size());
		
		//Match title all but some duedate
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("test", dueDate1);
		assertEquals(2, result2.size());
		assertEquals("test 1 bb", result2.get(0).getTitle());
		assertEquals("test 2 ee", result2.get(1).getTitle());
		
		List<Todo> result3 = this.todoService.findByTitleAndDueDate("test", dueDate2);
		assertEquals(1, result3.size());
		assertEquals("test 3 bb", result3.get(0).getTitle());
		
		//Match some duadate but some title
		List<Todo> result4 = this.todoService.findByTitleAndDueDate("sdfsfd", dueDate2);
		assertEquals(0, result4.size());
		
		//Match title all but some duedate
		List<Todo> result5 = this.todoService.findByTitleAndDueDate("bb", dueDate2);
		assertEquals(1, result5.size());
		assertEquals("test 3 bb", result5.get(0).getTitle());
	}
	
	@Test
	public void testAddTodoSuccess() {
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result1.size());
		LocalDateTime duedate = LocalDateTime.now().plusDays(6);
		Todo res = this.todoService.addTodo("new title", "new desc", duedate);
		
		assertEquals("new title", res.getTitle());
		assertEquals("new desc", res.getDescription());
		assertEquals(duedate, res.getDueDate());
		
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(4, result2.size());
		assertEquals("new title", result2.get(3).getTitle());
		assertEquals("new desc", result2.get(3).getDescription());
		assertEquals(duedate, result2.get(3).getDueDate());
	}
	
	@Test
	public void testUpdateTodoSuccess() {
		//Find All
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result1.size());
		LocalDateTime duedate = LocalDateTime.now().plusDays(6);
		Todo res = this.todoService.updateTodo(result1.get(0).getId(), "new title", "new desc", duedate);
		
		assertEquals("new title", res.getTitle());
		assertEquals("new desc", res.getDescription());
		assertEquals(duedate, res.getDueDate());
		
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result2.size());
		assertEquals(res.getId(), result2.get(2).getId());
		assertEquals("new title", result2.get(2).getTitle());
		assertEquals("new desc", result2.get(2).getDescription());
		assertEquals(duedate, result2.get(2).getDueDate());
	}
	
	@Test
	public void testUpdateTodoNotfound() {
		try {
			LocalDateTime duedate = LocalDateTime.now().plusDays(6);
			this.todoService.updateTodo("12231312", "new title", "new desc", duedate);
			fail("Must throw exception");
		}catch(TodoServiceNotfoundException e) {
			
		}catch (Exception e) {
			fail("Worng exception type");
		}
	}
	
	@Test
	public void testDeleteTodoSuccess() {
		//Find All
		List<Todo> result1 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(3, result1.size());
		
		this.todoService.deleteTodo(result1.get(0).getId());
		
		List<Todo> result2 = this.todoService.findByTitleAndDueDate("", null);
		assertEquals(2, result2.size());
		
		assertNotEquals(result1.get(0).getId(), result2.get(0).getId());
		assertNotEquals(result1.get(0).getId(), result2.get(1).getId());
	}
	
	@Test
	public void testDeleteTodoNotfound() {
		try {
			this.todoService.deleteTodo("12231312");
			fail("Must throw exception");
		}catch(TodoServiceNotfoundException e) {
			
		}catch (Exception e) {
			fail("Worng exception type");
		}
	}
}
