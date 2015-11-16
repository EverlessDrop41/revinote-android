package com.everlesslycoding.revinote.Subjects;

import java.io.Serializable;

/**
 * Created by Emily on 13/11/2015.
 */
public class Subject implements Serializable {
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

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
