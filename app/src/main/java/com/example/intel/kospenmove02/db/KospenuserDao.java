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

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioOne();

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioTwo();

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioThree();

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourA();

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourB();

    @Query("SELECT kospenuser.ic, kospenuser.name " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourC();

    @Query("SELECT kospenuser.name, kospenuser.ic " +
            "FROM kospenuser " +
            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND ic NOT in (SELECT ic FROM kospenuserglobal)")
    LiveData<List<Kospenuser>> loadScenarioFive();

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
