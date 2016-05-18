package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Sulton on 5/17/2016.
 */
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {
    List<ToDo> findByToDoType(String toDoType);
//  List<ToDo> findOne(String toDo);

}
