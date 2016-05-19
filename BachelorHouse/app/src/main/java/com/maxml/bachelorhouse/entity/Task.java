package com.maxml.bachelorhouse.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Maxml on 16.05.2016.
 */
public class Task {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String date;
    @DatabaseField(foreign = true, columnName = "room_id")
    private Project project;

    public Task(String title, String date, Project project) {
        this.title = title;
        this.date = date;
        this.project = project;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", project=" + project +
                '}';
    }
}

