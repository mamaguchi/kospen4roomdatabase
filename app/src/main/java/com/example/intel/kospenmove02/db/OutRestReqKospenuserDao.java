package com.example.intel.kospenmove02.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface OutRestReqKospenuserDao {

    @Query("select * from outrestreqkospenuser")
    List<OutRestReqKospenuser> loadAll();

    @Query("select * from outrestreqkospenuser")
    LiveData<List<OutRestReqKospenuser>> loadAllOutRestReqKospenusers();

    @Query("select * from outrestreqkospenuser where ic = :ic")
    LiveData<OutRestReqKospenuser> loadOutRestReqKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertOutRestReqKospenuser(OutRestReqKospenuser outRestReqKospenuser);

    @Delete
    void deleteOutRestReqKospenuser(OutRestReqKospenuser outRestReqKospenuser);

    @Query("DELETE from outrestreqkospenuser where ic = :ic")
    void deleteOutRestReqKospenuserByIc(String ic);

    @Query("delete from outrestreqkospenuser")
    void deleteAll();

}
