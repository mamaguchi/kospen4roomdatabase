package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.State;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface StateDao {

    @Query("SELECT * from state")
    LiveData<List<State>> loadAllState();

    @Query("SELECT * from state WHERE id = :id")
    LiveData<State> loadStateById(int id);

    @Insert(onConflict = IGNORE)
    void insertState(State state);

    @Delete
    void deleteState(State state);

    @Query("DELETE from state")
    void deleteAll();

}
