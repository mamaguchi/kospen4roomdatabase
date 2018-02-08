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


@Entity
public class KospenuserGlobal {

    private String timestamp;

    @PrimaryKey
    @NonNull
    private String ic;

    private String name;

    @TypeConverters(StateConverter.class)
    private State state;
    @TypeConverters(RegionConverter.class)
    private Region region;
    @TypeConverters(SubregionConverter.class)
    private Subregion subregion;
    @TypeConverters(LocalityConverter.class)
    private Locality locality;

    /*
    |
    |   Constructor
    |
    */
    public KospenuserGlobal(String timestamp, @NonNull String ic, String name, State state, Region region, Subregion subregion, Locality locality) {
        this.timestamp = timestamp;
        this.ic = ic;
        this.name = name;
        this.state = state;
        this.region = region;
        this.subregion = subregion;
        this.locality = locality;
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
}
