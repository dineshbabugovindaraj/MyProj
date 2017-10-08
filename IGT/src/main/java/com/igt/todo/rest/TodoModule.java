package com.igt.todo.rest;

/**
 * @author Dinesh
 */
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.igt.todo.model.TodoTask;
import com.igt.todo.service.TodoService;
import com.igt.todo.util.CustomErrorType;

@RestController
@RequestMapping("/t")
public class TodoModule {

	public static final Logger logger = LoggerFactory
			.getLogger(TodoModule.class);

	@Autowired
	TodoService todoService;

	/**
	 * get all tasks
	 */
	@RequestMapping(value = "/task/", method = RequestMethod.GET)
	public ResponseEntity<List<TodoTask>> listAllTasks() {
		logger.info("Fetching Tasks");
		List<TodoTask> tasks = todoService.findAllTasks();
		if (tasks.isEmpty()) {
			tasks = new ArrayList<TodoTask>();
		}
		return new ResponseEntity<List<TodoTask>>(tasks, HttpStatus.OK);
	}

	/**
	 * get specific task
	 */
	@RequestMapping(value = "/task/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getTask(@PathVariable("id") long id) {
		logger.info("Fetching Task with id {}", id);
		TodoTask task = todoService.findById(id);
		if (task == null) {
			logger.error("Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Task with id " + id
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TodoTask>(task, HttpStatus.OK);
	}

	/**
	 * creating task
	 */
	@RequestMapping(value = "/task/", method = RequestMethod.POST)
	public ResponseEntity<?> createTask(@RequestBody TodoTask todo,
			UriComponentsBuilder ucBuilder) {
		logger.info("Creating Task : {}", todo);

		if (todoService.isTaskExist(todo)) {
			logger.error("Unable to create. A Task with name {} already exist",
					todo.getTask());
			return new ResponseEntity(new CustomErrorType(
					"Unable to create. A Task with name " + todo.getTask()
							+ " already exist."), HttpStatus.CONFLICT);
		}
		todoService.saveTask(todo);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}")
				.buildAndExpand(todo.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * update task
	 */
	@RequestMapping(value = "/task/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTask(@PathVariable("id") long id,
			@RequestBody TodoTask todo) {
		logger.info("Updating Task with id {}", id);

		TodoTask currentTask = todoService.findById(id);

		if (currentTask == null) {
			logger.error("Unable to update. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType(
					"Unable to upate. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentTask.setTask(todo.getTask());
		currentTask.setDesc(todo.getDesc());
		currentTask.setStatus(todo.getStatus());

		todoService.updateTask(currentTask);
		return new ResponseEntity<TodoTask>(currentTask, HttpStatus.OK);
	}

	/**
	 * delete task
	 */
	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTask(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Task with id {}", id);

		TodoTask user = todoService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. Task with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType(
					"Unable to delete. Task with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		todoService.deleteTaskById(id);
		return new ResponseEntity<TodoTask>(HttpStatus.NO_CONTENT);
	}

	/**
	 * delete all tasks
	 */
	@RequestMapping(value = "/task/", method = RequestMethod.DELETE)
	public ResponseEntity<TodoTask> deleteAllTasks() {
		logger.info("Deleting All Tasks");

		todoService.deleteAllTasks();
		return new ResponseEntity<TodoTask>(HttpStatus.NO_CONTENT);
	}

}