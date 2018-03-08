package com.example.intel.kospenmove02.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.intel.kospenmove02.db.entity.Kospenuser;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface KospenuserDao {

    // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
//    LiveData<List<Kospenuser>> loadScenarioOne();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
//    LiveData<List<Kospenuser>> loadScenarioTwo();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
//    LiveData<List<Kospenuser>> loadScenarioThree();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
//    LiveData<List<Kospenuser>> loadScenarioFourA();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
//    LiveData<List<Kospenuser>> loadScenarioFourB();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
//    LiveData<List<Kospenuser>> loadScenarioFourC();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser " +
//            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND ic NOT in (SELECT ic FROM kospenuserglobal)")
//    LiveData<List<Kospenuser>> loadScenarioFive();


    // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioOne();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioTwo();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioThree();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourA();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourB();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourC();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
//            "FROM kospenuser " +
//            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND ic NOT in (SELECT ic FROM kospenuserglobal)")
//    List<Kospenuser> loadScenarioFive();


    // VERSION 3: to update 'version' column of both OutRestReqKospenuser & InDBQueryKospenuser)
    // on every REST request of inside-locality kospenusers list from laravel server.
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioOne();
//
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioTwo();
//
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
//    List<Kospenuser> loadScenarioThree();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourA();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourB();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
//    List<Kospenuser> loadScenarioFourC();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser " +
//            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND ic NOT in (SELECT ic FROM kospenuserglobal)")
//    List<Kospenuser> loadScenarioFive();


    // VERSION 4:
    // -To update 'version' column of both OutRestReqKospenuser & InDBQueryKospenuser)
    // on every REST request of inside-locality kospenusers list from laravel server.
    // -Filters out rows with 'softDel' set to true.
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp > kospenuserserver.timestamp AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioOne();
//
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp < kospenuserserver.timestamp AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioTwo();
//
//    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser INNER JOIN kospenuserserver " +
//            "ON kospenuserserver.ic = kospenuser.ic " +
//            "WHERE kospenuser.timestamp = kospenuserserver.timestamp AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioThree();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp > kospenuserglobal.timestamp) AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioFourA();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp < kospenuserglobal.timestamp) AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioFourB();
//
//    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser JOIN kospenuserglobal " +
//            "ON kospenuser.ic = kospenuserglobal.ic " +
//            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND (kospenuser.timestamp = kospenuserglobal.timestamp) AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioFourC();
//
//    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
//            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
//            "FROM kospenuser " +
//            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
//            "AND ic NOT in (SELECT ic FROM kospenuserglobal) AND kospenuser.softDel = 0")
//    List<Kospenuser> loadScenarioFive();


    // VERSION 5:
    // -To update 'version' column of both OutRestReqKospenuser & InDBQueryKospenuser)
    // on every REST request of inside-locality kospenusers list from laravel server.
    // -Filters out rows with 'softDel' set to true.
    // -Modified 'loadScenarioTwo()' & 'loadScenarioFourB()' to apply changes to
    // local sqlite DB immediately.
    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp > kospenuserserver.timestamp AND kospenuser.softDel = 0")
    List<Kospenuser> loadScenarioOne();

    // Update a column using column value from another table in a 'inner-join-like' way in SQlite.
    @Query("UPDATE kospenuser SET " +
            "version = (SELECT kospenuserserver.version FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "timestamp = (SELECT kospenuserserver.timestamp FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "fk_gender = (SELECT kospenuserserver.fk_gender FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "name = (SELECT kospenuserserver.name FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "address = (SELECT kospenuserserver.address FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "firstRegRegion = (SELECT kospenuserserver.firstRegRegion FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic), " +
            "dirty = 0 " +
            "WHERE timestamp < (SELECT kospenuserserver.timestamp FROM kospenuserserver WHERE kospenuser.ic = kospenuserserver.ic) " +
            "AND softDel = 0")
    void loadScenarioTwo();

    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp = kospenuserserver.timestamp AND kospenuser.softDel = 0")
    List<Kospenuser> loadScenarioThree();

    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp > kospenuserglobal.timestamp) AND kospenuser.softDel = 0")
    List<Kospenuser> loadScenarioFourA();

    @Query("DELETE from kospenuser WHERE " +
            "ic NOT in (SELECT kospenuserserver.ic FROM kospenuserserver) " +
            "AND timestamp < (SELECT kospenuserglobal.timestamp FROM kospenuserglobal WHERE kospenuser.ic = kospenuserglobal.ic) " +
            "AND softDel = 0")
    void loadScenarioFourB();

    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp = kospenuserglobal.timestamp) AND kospenuser.softDel = 0")
    List<Kospenuser> loadScenarioFourC();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion , kospenuser.softDel, kospenuser.dirty " +
            "FROM kospenuser " +
            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND ic NOT in (SELECT ic FROM kospenuserglobal) AND kospenuser.softDel = 0")
    List<Kospenuser> loadScenarioFive();


    @Query("SELECT COUNT(*) from kospenuser WHERE dirty=1 AND softDel=0")
    int checkIfKospenuserDirty();

    @Query("SELECT * from kospenuser")
    LiveData<List<Kospenuser>> loadAllKospenusers();

    @Query("SELECT * from kospenuser WHERE ic = :ic")
    LiveData<Kospenuser> loadKospenuserByIc(String ic);

    @Insert(onConflict = IGNORE)
    void insertKospenuser(Kospenuser kospenuser);

    @Query("UPDATE kospenuser SET " +
            "version = :version, " +
            "fk_gender = :fk_gender, " +
            "fk_state = :fk_state, " +
            "fk_region = :fk_region, " +
            "fk_subregion = :fk_subregion, " +
            "fk_locality = :fk_locality, " +
            "name = :name, " +
            "address = :address, " +
            "firstRegRegion = :firstRegRegion WHERE " +
            "ic = :ic")
    void updateKospenuser(int version, int fk_gender, int fk_state, int fk_region, int fk_subregion, int fk_locality,
                          String name, String address, String firstRegRegion, String ic);

    @Query("UPDATE kospenuser SET " +
            "version = :version WHERE " +
            "ic = :ic")
    void updateVersionColKospenuser(int version, String ic);

    @Query("UPDATE kospenuser SET " +
            "softDel = 1 WHERE " +
            "ic in (SELECT ic FROM outrestreqkospenuser WHERE outRestReqFailCounter >= 3)")
    void setKospenuserSoftDelColTrueWith3orMoreFailCounter();

    @Query("UPDATE kospenuser SET " +
            "dirty = 0 WHERE " +
            "softDel = 1")
    void setDirtyColFalseIfSoftDelColTrueKospenuser();

    @Query("UPDATE kospenuser SET " +
            "dirty = 1 WHERE " +
            "ic = :ic")
    void setDirtyColTrueKospenuser(String ic);

    @Query("UPDATE kospenuser SET " +
            "dirty = 0 WHERE " +
            "ic = :ic")
    void setDirtyColFalseKospenuser(String ic);

    @Query("UPDATE kospenuser SET " +
            "softDel = 1 WHERE " +
            "ic = :ic")
    void setSoftDelColTrueKospenuser(String ic);

    @Delete
    void deleteUser(Kospenuser kospenuser);

    @Query("DELETE from kospenuser where ic = :ic")
    void deleteKospenuserByIc(String ic);

    @Query("DELETE from kospenuser")
    void deleteAll();

}
