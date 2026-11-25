package com.lipe.service;

import com.lipe.exception.TaskNotFoundException;
import com.lipe.model.Task;
import com.lipe.repository.TaskRepository;

import java.awt.*;
import java.util.ArrayList;

public class TaskService {

    private TaskRepository repository;


    public TaskService (TaskRepository repository){
        this.repository = repository;
    }

    public Task create( Task task){
        String taskTitle = task.getTitle();

        if (taskTitle == null || taskTitle.isBlank()){
            throw new IllegalArgumentException("invalid arguments");
        }

        Task taskCreated = repository.save(task);
        return taskCreated;
    }

    public ArrayList<Task> findtasks (){
        return repository.findAll();
    }

    public Task findTaskById (Long id){
        Task taskFindById = repository.findById(id);

        if (taskFindById == null){
            throw new TaskNotFoundException("task not found");
        }

        return taskFindById;
    }

    public void deleteTask (Long id){
        boolean deleted = repository.delete(id);

        if (deleted == false){
            throw new TaskNotFoundException("task not found");
        }

    }

    public Task updateTask (Long id, Task updatedTask){
        Task existTask = findTaskById(id);
        if (updatedTask.getTitle() == null || updatedTask.getTitle().isBlank()){
            throw new IllegalArgumentException("invalid arguments");
        }
        return repository.update(id, updatedTask);
    }

}
