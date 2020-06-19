package com.example.task_list.helper.DAO;

import com.example.task_list.model.Task;

import java.util.ArrayList;

public interface ITaskDAO {

    public boolean save(Task task);
    public boolean update(Task task);
    public boolean delete(Task task);
    public ArrayList<Task> list();
}
