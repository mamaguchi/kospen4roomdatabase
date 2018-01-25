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

    @Query("select * from kospenuser")
    LiveData<List<Kospenuser>> loadAllKospenusers();

    @Query("select * from kospenuser where ic = :ic")
    LiveData<Kospenuser> loadKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertKospenuser(Kospenuser kospenuser);

    @Delete
    void deleteUser(Kospenuser kospenuser);

    @Query("delete from kospenuser")
    void deleteAll();

}
