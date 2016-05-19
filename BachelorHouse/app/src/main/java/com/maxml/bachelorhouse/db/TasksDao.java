package com.maxml.bachelorhouse.db;

import android.content.Context;

import com.maxml.bachelorhouse.entity.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxml on 16.05.2016.
 */
public class TasksDao {

    private OrmLiteHelper helper;

    public TasksDao(Context context) {
        this.helper = new OrmLiteHelper(context);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = helper.getTasksDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void save(final Task task) {
        try {
            helper.getTasksDao().create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(final Task task) {
        try {
            helper.getTasksDao().update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Task task) {
        try {
            helper.getTasksDao().delete(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
