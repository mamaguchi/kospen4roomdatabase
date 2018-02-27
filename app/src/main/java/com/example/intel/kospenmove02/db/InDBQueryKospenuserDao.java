package com.example.intel.kospenmove02.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.InDBQueryKospenuser;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface InDBQueryKospenuserDao {

    @Query("select * from indbquerykospenuser")
    LiveData<List<InDBQueryKospenuser>> loadAllInDBQueryKospenusers();

    @Query("select * from indbquerykospenuser where ic = :ic")
    LiveData<InDBQueryKospenuser> loadInDBQueryKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertInDBQueryKospenuser(InDBQueryKospenuser inDBQueryKospenuser);

    @Delete
    void deleteInDBQueryKospenuser(InDBQueryKospenuser inDBQueryKospenuser);

    @Query("DELETE from indbquerykospenuser where ic = :ic")
    void deleteInDBQueryKospenuserByIc(String ic);

    @Query("delete from indbquerykospenuser")
    void deleteAll();

}
