package com.example.intel.kospenmove02.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/*
|
|   Singleton Pattern - for Database instance
|
 */
@Database(entities = {Kospenuser.class, Screening.class,
                    KospenuserServer.class, KospenuserGlobal.class,
                    OutRestReqKospenuser.class, InDBQueryKospenuser.class}, version=6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

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
