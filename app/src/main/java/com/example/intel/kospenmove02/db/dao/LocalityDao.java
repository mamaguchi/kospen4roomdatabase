package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Locality;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface LocalityDao {

    @Query("SELECT * from locality")
    LiveData<List<Locality>> loadAllLocality();

    @Query("SELECT * from locality WHERE id = :id")
    LiveData<Locality> loadLocalityById(int id);

    @Insert(onConflict = IGNORE)
    void insertLocality(Locality locality);

    @Delete
    void deleteLocality(Locality locality);

    @Query("DELETE from locality")
    void deleteAll();

}
