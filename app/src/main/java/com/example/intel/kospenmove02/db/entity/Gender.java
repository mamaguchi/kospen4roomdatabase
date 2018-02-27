package com.example.intel.kospenmove02.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Gender {

    @PrimaryKey
    @NonNull
    private int id;

    private String name;


    /*
    |
    |   Constructor
    |
    */
    public Gender(@NonNull int id, String name) {
        this.id = id;
        this.name = name;
    }


    /*
    |
    |   Getter and Setter
    |
    */
    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
