package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.intel.kospenmove02.db.entity.Kospenuser;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface KospenuserDao {

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioOne();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioTwo();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
    LiveData<List<Kospenuser>> loadScenarioThree();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourA();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourB();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
    LiveData<List<Kospenuser>> loadScenarioFourC();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
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
