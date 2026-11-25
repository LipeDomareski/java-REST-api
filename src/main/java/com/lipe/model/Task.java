package com.lipe.model;

public class Task {
    private Long id;
    private String title;
    private String description;
    private boolean completed = false;


    public Task(String title, String description){
        this.title = title;
        this.description = description;
    }

    public Task (Long id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Task(){}

    //getters
    public String getDescription() {return description;}
    public String getTitle() {return title;}
    public Long getId() {return id;}
    public boolean getIsCompleted(){return completed;}

    // setters
//    public void setId(Long id) {this.id = id;}
    public void setCompleted(boolean completed) {this.completed = completed;}
    public void setDescription(String description) {this.description = description;}
    public void setTitle(String title) {this.title = title;}
}
