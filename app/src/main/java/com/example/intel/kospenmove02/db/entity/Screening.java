package com.example.intel.kospenmove02.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.converter.BooleanConverter;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = {
        @ForeignKey(entity=Kospenuser.class,
                    parentColumns = "ic",
                    childColumns = "fk_ic",
                    onDelete = CASCADE)})
public class Screening {

    @PrimaryKey
    @NonNull
    private int id;

    private String fk_ic;

//    @TypeConverters(DateConverter.class)
//    private LocalDateTime date;

    private String date;

    private double weight;

    private double height;

    private int systolic;

    private int diastolic;

    private double dxt;

    @TypeConverters(BooleanConverter.class)
    private boolean smoker;

    @TypeConverters(BooleanConverter.class)
    private boolean softDel;

    private int outRestReqFailCounter;


    /*
    |
    |   Constructor
    |
    */
    public Screening(@NonNull int id, String fk_ic, String date,
                     double weight, double height, int systolic, int diastolic, double dxt,
                     boolean smoker) {
        // VERSION 1: normal constructor
//        this.id = id;
//        this.fk_ic = fk_ic;
//        this.date = date;
//        this.weight = weight;
//        this.height = height;
//        this.systolic = systolic;
//        this.diastolic = diastolic;
//        this.dxt = dxt;
//        this.smoker = smoker;
//        this.softDel = softDel;

        // VERSION 2: constructor with optional arguments using constructor overloading,by chaining constructor using 'this'.
        this(id, fk_ic, date, weight, height, systolic, diastolic, dxt, smoker, false, 0);
    }

    @Ignore
    public Screening(@NonNull int id, String fk_ic, String date,
                     double weight, double height, int systolic, int diastolic, double dxt,
                     boolean smoker, boolean softDel, int outRestReqFailCounter) {
        this.id = id;
        this.fk_ic = fk_ic;
        this.date = date;
        this.weight = weight;
        this.height = height;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.dxt = dxt;
        this.smoker = smoker;
        this.softDel = softDel;
        this.outRestReqFailCounter = outRestReqFailCounter;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
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

    public double getDxt() {
        return dxt;
    }

    public void setDxt(double dxt) {
        this.dxt = dxt;
    }

    public boolean isSmoker() {
        return smoker;
    }

    public void setSmoker(boolean smoker) {
        this.smoker = smoker;
    }

    public boolean isSoftDel() {
        return softDel;
    }

    public void setSoftDel(boolean softDel) {
        this.softDel = softDel;
    }

    public int getOutRestReqFailCounter() {
        return outRestReqFailCounter;
    }

    public void setOutRestReqFailCounter(int outRestReqFailCounter) {
        this.outRestReqFailCounter = outRestReqFailCounter;
    }
}
