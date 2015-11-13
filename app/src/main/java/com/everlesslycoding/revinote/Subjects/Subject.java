package com.everlesslycoding.revinote.Subjects;

/**
 * Created by Emily on 13/11/2015.
 */
public class Subject {
    String name;
    String description;

    public String getDescription() {
        return description;
    }

    public Subject () {
        //Empty for firebase
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
