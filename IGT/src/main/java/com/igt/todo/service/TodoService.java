package com.igt.todo.service;
/**
 * @author Dinesh
 */

import java.util.List;

import com.igt.todo.model.TodoTask;

public interface TodoService {
	
	TodoTask findById(Long id);

	TodoTask findByName(String todo);

	void saveTask(TodoTask todo);

	void updateTask(TodoTask todo);

	void deleteTaskById(Long id);

	List<TodoTask> findAllTasks();

	boolean isTaskExist(TodoTask todo);

	void deleteAllTasks();
}