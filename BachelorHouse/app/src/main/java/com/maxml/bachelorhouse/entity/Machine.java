package com.maxml.bachelorhouse.entity;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Maxml on 16.05.2016.
 */
public class Machine {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int temperature;
    @DatabaseField
    private int wet;
    @DatabaseField(foreign = true, columnName = "room_id")
    private Room room;

    public Machine() {
    }

    public Machine(String name, int temperature, int wet, Room room) {
        this.name = name;
        this.temperature = temperature;
        this.wet = wet;
        this.room = room;
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

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWet() {
        return wet;
    }

    public void setWet(int wet) {
        this.wet = wet;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", wet=" + wet +
                ", room=" + room +
                '}';
    }
}
