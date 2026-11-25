package com.lipe.repository;

import com.lipe.model.Task;
import java.util.ArrayList;


public class TaskRepository {

    private ArrayList<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    // Create save the task
    public Task save(Task task) {

        Task newTask = new Task(nextId, task.getTitle(), task.getDescription());

        tasks.add(newTask);
        nextId++;

        return newTask;
    }

    // Find all Task in the array
    public ArrayList<Task> findAll(){
        return tasks;
    }

    // Find taks by ID
    public Task findById(Long id){
        for (Task task : tasks){

            if (task.getId().equals(id)){
                return task;
            }
        }
        return null;
    }

    // Delete task

    public boolean delete(long id){
        Task task = findById(id);
            if (task != null) {
                tasks.remove(task);
                return true;
            }
                return false;
    }


    public Task update (Long id, Task updatedTask){
        Task task = findById(id);

        if (task != null)
        {
            task.setTitle(updatedTask.getTitle());
            task.setCompleted(updatedTask.getIsCompleted());
            task.setDescription(updatedTask.getDescription());

            return task;
        }
        return null;
    }
}
   