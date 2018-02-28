package com.example.intel.kospenmove02.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.BooleanConverter;
import com.example.intel.kospenmove02.db.entity.Kospenuser;


@Entity(foreignKeys = {
        @ForeignKey(entity=Kospenuser.class,
                    parentColumns = "ic",
                    childColumns = "fk_ic")})
public class Screening {

    @PrimaryKey
    @NonNull
    private String id;

    private String fk_ic;

//    @TypeConverters(DateConverter.class)
//    private LocalDateTime date;

    private String date;

    private int weight;

    private int height;

    private int systolic;

    private int diastolic;

    private int dxt;

    @TypeConverters(BooleanConverter.class)
    private boolean smoker;

    @TypeConverters(BooleanConverter.class)
    private boolean sendToServer;


    /*
    |
    |   Constructor
    |
    */
    public Screening(@NonNull String id, String fk_ic, String date,
                     int weight, int height, int systolic, int diastolic, int dxt,
                     boolean smoker, boolean sendToServer) {
        this.id = id;
        this.fk_ic = fk_ic;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.dxt = dxt;
        this.smoker = smoker;
        this.sendToServer = sendToServer;
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

    public String getFk_ic() {
        return fk_ic;
    }

    public void setFk_ic(String fk_ic) {
        this.fk_ic = fk_ic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getDxt() {
        return dxt;
    }

    public void setDxt(int dxt) {
        this.dxt = dxt;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public boolean isSendToServer() {
        return sendToServer;
    }

    public void setSendToServer(boolean sendToServer) {
        this.sendToServer = sendToServer;
    }
}
