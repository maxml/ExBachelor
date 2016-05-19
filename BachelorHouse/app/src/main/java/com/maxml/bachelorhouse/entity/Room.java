package com.maxml.bachelorhouse.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

/**
 * Created by Maxml on 16.05.2016.
 */
public class Room {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int temperature;
    @DatabaseField
    private int wet;
    @DatabaseField
    private boolean isEnabled;
    @ForeignCollectionField
    private Collection<Machine> machines;

    public Room() {
    }

    public Room(String name, int temperature, int wet, boolean isEnabled) {
        this.name = name;
        this.wet = wet;
        this.temperature = temperature;
        this.isEnabled = isEnabled;
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWet() {
        return wet;
    }

    public void setWet(int wet) {
        this.wet = wet;
    }

    public Collection<Machine> getMachines() {
        return machines;
    }

    public void setMachines(Collection<Machine> machines) {
        this.machines = machines;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", wet=" + wet +
                ", isEnabled=" + isEnabled +
                ", machines=" + machines +
                '}';
    }
}
