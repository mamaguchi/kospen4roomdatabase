package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Gender;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface GenderDao {

    @Query("SELECT * from gender")
    LiveData<List<Gender>> loadAllGender();

    @Query("SELECT * from gender WHERE id = :id")
    LiveData<Gender> loadGenderById(int id);

    @Insert(onConflict = IGNORE)
    void insertGender(Gender gender);

    @Delete
    void deleteGender(Gender gender);

    @Query("DELETE from gender")
    void deleteAll();

}
