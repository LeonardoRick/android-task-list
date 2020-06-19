package com.example.task_list.model;

import java.io.Serializable;

public class Task implements Serializable {

    private Long id;
    private String taskText;

    public Task() {}

    public Task(Long id, String taskText) {
        this.id = id;
        this.taskText = taskText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }
}
