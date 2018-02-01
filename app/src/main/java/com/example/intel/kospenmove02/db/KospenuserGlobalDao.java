package com.example.intel.kospenmove02.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface KospenuserGlobalDao {

    @Query("select * from kospenuserglobal")
    LiveData<List<KospenuserGlobal>> loadAllKospenusersGlobal();

    @Query("select * from kospenuserglobal where ic = :ic")
    LiveData<KospenuserGlobal> loadKospenuserGlobalByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertKospenuserGlobal(KospenuserGlobal kospenuserGlobal);

    @Delete
    void deleteKospenuserGlobal(KospenuserGlobal kospenuserGlobal);

    @Query("delete from kospenuserglobal")
    void deleteAll();

}
