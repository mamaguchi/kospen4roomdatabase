package com.example.intel.kospenmove02.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface KospenuserDao {

    @Query("SELECT * from kospenuser")
    LiveData<List<Kospenuser>> loadAllKospenusers();

    @Query("SELECT * from kospenuser WHERE ic = :ic")
    LiveData<Kospenuser> loadKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertKospenuser(Kospenuser kospenuser);

    @Delete
    void deleteUser(Kospenuser kospenuser);

    @Query("DELETE from kospenuser")
    void deleteAll();

}
