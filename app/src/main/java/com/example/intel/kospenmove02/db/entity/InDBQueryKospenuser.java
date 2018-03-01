package com.example.intel.kospenmove02.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.converter.InDBQueryConverter;
import com.example.intel.kospenmove02.db.converter.InDBQueryConverter.InDBQuery;


@Entity(foreignKeys = {
        @ForeignKey(entity=Gender.class,
                parentColumns = "id",
                childColumns = "fk_gender"),
        @ForeignKey(entity=State.class,
                parentColumns = "id",
                childColumns = "fk_state"),
        @ForeignKey(entity=Region.class,
                parentColumns = "id",
                childColumns = "fk_region"),
        @ForeignKey(entity=Subregion.class,
                parentColumns = "id",
                childColumns = "fk_subregion"),
        @ForeignKey(entity=Locality.class,
                parentColumns = "id",
                childColumns = "fk_locality")})
public class InDBQueryKospenuser {

    private String timestamp;

    @TypeConverters(InDBQueryConverter.class)
    private InDBQuery inDBQueryStatus;

    @PrimaryKey
    @NonNull
    private String ic;

    private String name;

    private String address;

    // Version 2:
//    @TypeConverters(StateConverter.class)
//    private State state;
//    @TypeConverters(RegionConverter.class)
//    private Region region;
//    @TypeConverters(SubregionConverter.class)
//    private Subregion subregion;
//    @TypeConverters(LocalityConverter.class)
//    private Locality locality;
    //
    // Version 3:
    private int fk_gender;
    private int fk_state;
    private int fk_region;
    private int fk_subregion;
    private int fk_locality;

    private String firstRegRegion;

    private int version;

    /*
    |
    |   Constructor
    |
    */

    public InDBQueryKospenuser(String timestamp, InDBQuery inDBQueryStatus, @NonNull String ic, String name, String address,
                               int fk_gender, int fk_state, int fk_region, int fk_subregion, int fk_locality, String firstRegRegion,
                               int version) {
        this.timestamp = timestamp;
        this.inDBQueryStatus = inDBQueryStatus;
        this.ic = ic;
        this.name = name;
        this.address = address;
        this.fk_gender = fk_gender;
        this.fk_state = fk_state;
        this.fk_region = fk_region;
        this.fk_subregion = fk_subregion;
        this.fk_locality = fk_locality;
        this.firstRegRegion = firstRegRegion;
        this.version = version;
    }

    public InDBQueryKospenuser(Kospenuser kospenuser) {
        this.timestamp = kospenuser.getTimestamp();
        this.inDBQueryStatus = null;
        this.ic = kospenuser.getIc();
        this.name = kospenuser.getName();
        this.fk_gender = kospenuser.getFk_gender();
        this.address = kospenuser.getAddress();
        this.fk_state = kospenuser.getFk_state();
        this.fk_region = kospenuser.getFk_region();
        this.fk_subregion = kospenuser.getFk_subregion();
        this.fk_locality = kospenuser.getFk_locality();
        this.firstRegRegion = kospenuser.getFirstRegRegion();
        this.version = kospenuser.getVersion();
    }

    public InDBQueryKospenuser(KospenuserServer kospenuserServer) {
        this.timestamp = kospenuserServer.getTimestamp();
        this.inDBQueryStatus = null;
        this.ic = kospenuserServer.getIc();
        this.name = kospenuserServer.getName();
        this.fk_gender = kospenuserServer.getFk_gender();
        this.address = kospenuserServer.getAddress();
        this.fk_state = kospenuserServer.getFk_state();
        this.fk_region = kospenuserServer.getFk_region();
        this.fk_subregion = kospenuserServer.getFk_subregion();
        this.fk_locality = kospenuserServer.getFk_locality();
        this.firstRegRegion = kospenuserServer.getFirstRegRegion();
        this.version = kospenuserServer.getVersion();
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

    public int getFk_gender() {
        return fk_gender;
    }

    public void setFk_gender(int fk_gender) {
        this.fk_gender = fk_gender;
    }

    public int getFk_state() {
        return fk_state;
    }

    public void setFk_state(int fk_state) {
        this.fk_state = fk_state;
    }

    public int getFk_region() {
        return fk_region;
    }

    public void setFk_region(int fk_region) {
        this.fk_region = fk_region;
    }

    public int getFk_subregion() {
        return fk_subregion;
    }

    public void setFk_subregion(int fk_subregion) {
        this.fk_subregion = fk_subregion;
    }

    public int getFk_locality() {
        return fk_locality;
    }

    public void setFk_locality(int fk_locality) {
        this.fk_locality = fk_locality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstRegRegion() {
        return firstRegRegion;
    }

    public void setFirstRegRegion(String firstRegRegion) {
        this.firstRegRegion = firstRegRegion;
    }

    public InDBQuery getInDBQueryStatus() {
        return inDBQueryStatus;
    }

    public void setInDBQueryStatus(InDBQuery inDBQueryStatus) {
        this.inDBQueryStatus = inDBQueryStatus;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}