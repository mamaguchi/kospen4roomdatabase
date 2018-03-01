package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.KospenuserServer;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface KospenuserServerDao {

    // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//    @Query("SELECT kospenuserserver.version, kospenuserserver.timestamp, kospenuserserver.ic, kospenuserserver.name, kospenuserserver.fk_gender, kospenuserserver.address, " +
//            "kospenuserserver.fk_state, kospenuserserver.fk_region, kospenuserserver.fk_subregion, kospenuserserver.fk_locality, kospenuserserver.firstRegRegion " +
//            "FROM kospenuserserver " +
//            "WHERE ic NOT in (SELECT ic FROM kospenuser)")
//    LiveData<List<KospenuserServer>> loadScenarioSix();


    // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
    @Query("SELECT kospenuserserver.version, kospenuserserver.timestamp, kospenuserserver.ic, kospenuserserver.name, kospenuserserver.fk_gender, kospenuserserver.address, " +
            "kospenuserserver.fk_state, kospenuserserver.fk_region, kospenuserserver.fk_subregion, kospenuserserver.fk_locality, kospenuserserver.firstRegRegion " +
            "FROM kospenuserserver " +
            "WHERE ic NOT in (SELECT ic FROM kospenuser)")
    List<KospenuserServer> loadScenarioSix();

    @Query("SELECT * from kospenuserserver")
    LiveData<List<KospenuserServer>> loadAllKospenusersServer();

    @Query("SELECT * from kospenuserserver WHERE ic = :ic")
    LiveData<KospenuserServer> loadKospenuserServerByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertKospenuserServer(KospenuserServer kospenuserServer);

    @Delete
    void deleteKospenuserServer(KospenuserServer kospenuserServer);

    @Query("DELETE from kospenuserserver")
    void deleteAll();

}