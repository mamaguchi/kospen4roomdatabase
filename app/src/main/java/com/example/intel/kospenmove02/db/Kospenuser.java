package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.GenderConverter.Gender;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Kospenuser {


    private String id;

//    @TypeConverters(DateConverter.class)
//    private LocalDateTime timestamp;

    private String timestamp;

    @PrimaryKey
    @NonNull
    private String ic;

    private String name;

    @TypeConverters(GenderConverter.class)
    private Gender gender;

    private String address;

    private String userRegion;

    private String firstRegRegion;


    /*
    |
    |   Constructor
    |
    */
    public Kospenuser(@NonNull String id, String timestamp, String ic, String name,
                      Gender gender, String address,
                      String userRegion, String firstRegRegion) {
        this.id = id;
        this.timestamp = timestamp;
        this.ic = ic;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.userRegion = userRegion;
        this.firstRegRegion = firstRegRegion;
    }

    /*
    |
    |   Getter and Setter
    |
    */
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getFirstRegRegion() {
        return firstRegRegion;
    }

    public void setFirstRegRegion(String firstRegRegion) {
        this.firstRegRegion = firstRegRegion;
    }
}
