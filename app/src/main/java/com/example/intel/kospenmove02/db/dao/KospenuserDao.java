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


    // VERSION 3: to update 'version' column of local 'kospenuser'(that generates both OutRestReqKospenuser & InDBQueryKospenuser)
    // on every REST request of inside-locality kospenusers list from laravel server.
    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp > kospenuserserver.timestamp")
    List<Kospenuser> loadScenarioOne();

    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp < kospenuserserver.timestamp")
    List<Kospenuser> loadScenarioTwo();

    @Query("SELECT kospenuserserver.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser INNER JOIN kospenuserserver " +
            "ON kospenuserserver.ic = kospenuser.ic " +
            "WHERE kospenuser.timestamp = kospenuserserver.timestamp")
    List<Kospenuser> loadScenarioThree();

    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp > kospenuserglobal.timestamp)")
    List<Kospenuser> loadScenarioFourA();

    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp < kospenuserglobal.timestamp)")
    List<Kospenuser> loadScenarioFourB();

    @Query("SELECT kospenuserglobal.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser JOIN kospenuserglobal " +
            "ON kospenuser.ic = kospenuserglobal.ic " +
            "WHERE kospenuser.ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND (kospenuser.timestamp = kospenuserglobal.timestamp)")
    List<Kospenuser> loadScenarioFourC();

    @Query("SELECT kospenuser.version, kospenuser.timestamp, kospenuser.ic, kospenuser.name, kospenuser.fk_gender, kospenuser.address, " +
            "kospenuser.fk_state, kospenuser.fk_region, kospenuser.fk_subregion, kospenuser.fk_locality, kospenuser.firstRegRegion " +
            "FROM kospenuser " +
            "WHERE ic NOT in (SELECT ic FROM kospenuserserver) " +
            "AND ic NOT in (SELECT ic FROM kospenuserglobal)")
    List<Kospenuser> loadScenarioFive();


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
                          String name, String  address, String firstRegRegion, String ic);

    @Query("UPDATE kospenuser SET " +
            "version = :version WHERE " +
            "ic = :ic")
    void updateVersionColKospenuser(int version, String ic);

    @Delete
    void deleteUser(Kospenuser kospenuser);

    @Query("DELETE from kospenuser where ic = :ic")
    void deleteKospenuserByIc(String ic);

    @Query("DELETE from kospenuser")
    void deleteAll();

}
