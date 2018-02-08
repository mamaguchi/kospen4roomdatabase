package com.example.intel.kospenmove02.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface ScreeningDao {

    @Query("select * from screening")
    LiveData<List<Screening>> loadAllScreenings();

    @Query("select * from screening where fk_ic = :fk_ic")
    LiveData<Screening> loadScreeningByFkic(String fk_ic);

    @Insert(onConflict = IGNORE)
    void insertScreening(Screening screening);

    @Delete
    void deleteScreening(Screening screening);

    @Query("delete from screening")
    void deleteAll();
}
