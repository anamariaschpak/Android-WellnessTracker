package com.example.wellnesstracker;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Exercise {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name="";
    private String description="";
    private String category="";
    private String equipment ="";

    @Ignore
    public Exercise(){

    }

    public Exercise(String name, String description, String category, String equipment) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.equipment = equipment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getEquipment() {
        return equipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
