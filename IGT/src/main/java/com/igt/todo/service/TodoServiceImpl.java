package com.igt.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.igt.todo.model.TodoTask;
import com.igt.todo.repositories.TodoRepository;

/**
 * 
 * @author Dinesh
 *
 */

@Service("todService")
@Transactional
public class TodoServiceImpl implements TodoService{

	@Autowired
	private TodoRepository todoRepository;

	public TodoTask findById(Long id) {
		return todoRepository.findOne(id);
	}

	public TodoTask findByName(String todo) {
		return todoRepository.findByTask(todo);
	}

	public void saveTask(TodoTask todo) {
		todoRepository.save(todo);
	}

	public void updateTask(TodoTask todo){
		saveTask(todo);
	}

	public void deleteTaskById(Long id){
		todoRepository.delete(id);
	}

	public void deleteAllTasks(){
		todoRepository.deleteAll();
	}

	public List<TodoTask> findAllTasks(){
		return todoRepository.findAll();
	}

	public boolean isTaskExist(TodoTask todo) {
		return findByName(todo.getTask()) != null;
	}


}
