package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.intel.kospenmove02.db.dao.GenderDao;
import com.example.intel.kospenmove02.db.dao.InDBQueryKospenuserDao;
import com.example.intel.kospenmove02.db.dao.KospenuserDao;
import com.example.intel.kospenmove02.db.dao.KospenuserGlobalDao;
import com.example.intel.kospenmove02.db.dao.KospenuserServerDao;
import com.example.intel.kospenmove02.db.dao.LocalityDao;
import com.example.intel.kospenmove02.db.dao.OutRestReqKospenuserDao;
import com.example.intel.kospenmove02.db.dao.RegionDao;
import com.example.intel.kospenmove02.db.dao.ScreeningDao;
import com.example.intel.kospenmove02.db.dao.StateDao;
import com.example.intel.kospenmove02.db.dao.SubregionDao;

import com.example.intel.kospenmove02.db.entity.Gender;
import com.example.intel.kospenmove02.db.entity.InDBQueryKospenuser;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.KospenuserGlobal;
import com.example.intel.kospenmove02.db.entity.KospenuserServer;
import com.example.intel.kospenmove02.db.entity.Locality;
import com.example.intel.kospenmove02.db.entity.OutRestReqKospenuser;
import com.example.intel.kospenmove02.db.entity.Region;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.db.entity.State;
import com.example.intel.kospenmove02.db.entity.Subregion;

/*
|
|   Singleton Pattern - for Database instance
|
 */
@Database(entities = {Gender.class, State.class, Region.class, Subregion.class, Locality.class,
                    Kospenuser.class, Screening.class,
                    KospenuserServer.class, KospenuserGlobal.class,
                    OutRestReqKospenuser.class, InDBQueryKospenuser.class}, version=16)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract GenderDao genderModel();

    public abstract StateDao stateModel();

    public abstract RegionDao regionModel();

    public abstract SubregionDao subregionModel();

    public abstract LocalityDao localityModel();

    public abstract KospenuserDao kospenuserModel();

    public abstract ScreeningDao screeningModel();

    public abstract KospenuserServerDao kospenuserServerModel();

    public abstract KospenuserGlobalDao kospenuserGlobalModel();

    public abstract OutRestReqKospenuserDao outRestReqKospenuserModel();

    public abstract InDBQueryKospenuserDao inDBQueryKospenuserModel();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "kospendatabasev1")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
