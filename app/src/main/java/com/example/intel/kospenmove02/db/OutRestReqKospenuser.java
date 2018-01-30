package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.GenderConverter.Gender;
import com.example.intel.kospenmove02.db.StateConverter.State;
import com.example.intel.kospenmove02.db.RegionConverter.Region;
import com.example.intel.kospenmove02.db.SubregionConverter.Subregion;
import com.example.intel.kospenmove02.db.LocalityConverter.Locality;
import com.example.intel.kospenmove02.db.OutRestReqConverter.OutRestReq;


@Entity
public class OutRestReqKospenuser {

    private String timestamp;

    @TypeConverters(OutRestReqConverter.class)
    private OutRestReq outRestReqStatus;

    @PrimaryKey
    @NonNull
    private String ic;

    private String name;

    @TypeConverters(GenderConverter.class)
    private Gender gender;

    private String address;

    @TypeConverters(StateConverter.class)
    private State state;
    @TypeConverters(RegionConverter.class)
    private Region region;
    @TypeConverters(SubregionConverter.class)
    private Subregion subregion;
    @TypeConverters(LocalityConverter.class)
    private Locality locality;

    private String firstRegRegion;

    /*
    |
    |   Constructor
    |
    */
    public OutRestReqKospenuser(String timestamp, OutRestReq outRestReqStatus, @NonNull String ic, String name, Gender gender,
                                String address, State state, Region region, Subregion subregion, Locality locality, String firstRegRegion) {
        this.timestamp = timestamp;
        this.outRestReqStatus = outRestReqStatus;
        this.ic = ic;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.state = state;
        this.region = region;
        this.subregion = subregion;
        this.locality = locality;
        this.firstRegRegion = firstRegRegion;
    }

    /*
        |
        |   Getter and Setter
        |
        */
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getIc() {
        return ic;
    }

    public void setIc(@NonNull String ic) {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Subregion getSubregion() {
        return subregion;
    }

    public void setSubregion(Subregion subregion) {
        this.subregion = subregion;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public String getFirstRegRegion() {
        return firstRegRegion;
    }

    public void setFirstRegRegion(String firstRegRegion) {
        this.firstRegRegion = firstRegRegion;
    }

    public OutRestReq getOutRestReqStatus() {
        return outRestReqStatus;
    }

    public void setOutRestReqStatus(OutRestReq outRestReqStatus) {
        this.outRestReqStatus = outRestReqStatus;
    }
}
