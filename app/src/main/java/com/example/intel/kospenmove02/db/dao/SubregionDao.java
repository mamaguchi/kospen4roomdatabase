package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Subregion;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface SubregionDao {

    @Query("SELECT * from subregion")
    LiveData<List<Subregion>> loadAllSubregion();

    @Query("SELECT * from subregion WHERE id = :id")
    LiveData<Subregion> loadSubregionById(int id);

    @Insert(onConflict = IGNORE)
    void insertSubregion(Subregion subregion);

    @Delete
    void deleteSubregion(Subregion subregion);

    @Query("DELETE from subregion")
    void deleteAll();

}
