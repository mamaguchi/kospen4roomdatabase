package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Screening;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface ScreeningDao {

    @Query("SELECT COUNT(*) from screening WHERE softDel=0")
    int checkIfScreeningNotEmpty();

    @Query("select * from screening")
    LiveData<List<Screening>> loadAllScreenings();

    @Query("select * from screening where softDel = 0")
    List<Screening> loadAll();

    @Query("select * from screening where fk_ic = :fk_ic")
    LiveData<Screening> loadScreeningByFkic(String fk_ic);

    @Insert(onConflict = IGNORE)
    void insertScreening(Screening screening);

    @Query("UPDATE screening SET " +
            "outRestReqFailCounter = outRestReqFailCounter + 1 WHERE " +
            "id = :id")
    void incrementOutRestReqFailCounter(int id);

    @Query("UPDATE screening SET " +
            "softDel = 1 WHERE " +
            "fk_ic in (SELECT kospenuser.ic FROM kospenuser WHERE softDel = 1)")
    void setScreeningSoftDelColTrueWithSoftDeletedKospenuser();

    @Query("UPDATE screening SET " +
            "softDel = 1 WHERE " +
            "id in (SELECT id FROM screening WHERE outRestReqFailCounter >= 3)")
    void setScreeningSoftDelColTrueWith3orMoreFailCounter();

    @Query("DELETE from screening WHERE id = :id")
    void deleteScreeningById(int id);

    @Delete
    void deleteScreening(Screening screening);

    @Query("delete from screening")
    void deleteAll();
}
