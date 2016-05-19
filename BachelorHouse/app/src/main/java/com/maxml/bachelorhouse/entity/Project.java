package com.maxml.bachelorhouse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

/**
 * Created by Maxml on 18.05.2016.
 */
public class Project {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private boolean status;
    @DatabaseField
    private double pay;
    @DatabaseField
    private String date;
    @DatabaseField
    private String clientEmail;
    @DatabaseField
    private String text;
    @ForeignCollectionField
    private Collection<Task> tasks;

    public Project(String name, boolean status, double pay, String date, String clientEmail, String text) {
        this.name = name;
        this.status = status;
        this.pay = pay;
        this.date = date;
        this.clientEmail = clientEmail;
        this.text = text;
    }

    public Project() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", pay=" + pay +
                ", date='" + date + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
