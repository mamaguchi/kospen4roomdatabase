package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.intel.kospenmove02.db.entity.Gender;
import com.example.intel.kospenmove02.db.entity.Locality;
import com.example.intel.kospenmove02.db.entity.Region;
import com.example.intel.kospenmove02.db.entity.State;
import com.example.intel.kospenmove02.db.entity.Subregion;


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
public class Kospenuser {

    // Version 1:
//    @TypeConverters(DateConverter.class)
//    private LocalDateTime timestamp;
    //
    // Version 2:
    private String timestamp;

    @PrimaryKey
    @NonNull
    private String ic;

    private String name;

    // Version 3:
//    @TypeConverters(GenderConverter.class)
//    private Gender gender;

    private String address;

    // Version 1:
//    private String userRegion;
    //
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

    public Kospenuser(String timestamp, @NonNull String ic, String name, String address, int fk_gender,
                      int fk_state, int fk_region, int fk_subregion, int fk_locality, String firstRegRegion, int version) {
        this.timestamp = timestamp;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
