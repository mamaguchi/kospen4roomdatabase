package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.OutRestReqKospenuser;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface OutRestReqKospenuserDao {

    @Query("SELECT * from outrestreqkospenuser")
    List<OutRestReqKospenuser> loadAll();

    @Query("SELECT * from outrestreqkospenuser")
    LiveData<List<OutRestReqKospenuser>> loadAllOutRestReqKospenusers();

    @Query("SELECT * from outrestreqkospenuser where ic = :ic")
    LiveData<OutRestReqKospenuser> loadOutRestReqKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertOutRestReqKospenuser(OutRestReqKospenuser outRestReqKospenuser);

    @Query("UPDATE outrestreqkospenuser SET " +
            "outRestReqFailCounter = outRestReqFailCounter + 1 WHERE " +
            "ic = :ic")
    void incrementOutRestReqFailCounter(String ic);

    @Query("DELETE from outrestreqkospenuser where " +
            "outRestReqFailCounter >= 3")
    void deleteOutRestReqKospenuserWith3orMoreFailCounter();

    @Query("DELETE from outrestreqkospenuser where ic = :ic")
    void deleteOutRestReqKospenuserByIc(String ic);

    @Query("DELETE from outrestreqkospenuser")
    void deleteAll();

    @Delete
    void deleteOutRestReqKospenuser(OutRestReqKospenuser outRestReqKospenuser);

}
