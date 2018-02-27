package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Region;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface RegionDao {

    @Query("SELECT * from region")
    LiveData<List<Region>> loadAllRegion();

    @Query("SELECT * from region WHERE id = :id")
    LiveData<Region> loadRegionById(int id);

    @Insert(onConflict = IGNORE)
    void insertRegion(Region region);

    @Delete
    void deleteRegion(Region region);

    @Query("DELETE from region")
    void deleteAll();

}
