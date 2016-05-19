package com.maxml.bachelorhouse.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Maxml on 13.05.2016.
 */
public class User {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String email;
    @DatabaseField
    private String name;
    @DatabaseField
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
