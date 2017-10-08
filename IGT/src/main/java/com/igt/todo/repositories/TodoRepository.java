package com.igt.todo.repositories;
/**
 * @author Dinesh
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.igt.todo.model.TodoTask;

@Repository
public interface TodoRepository extends JpaRepository<TodoTask, Long> {

    TodoTask findByTask(String todo);

}
