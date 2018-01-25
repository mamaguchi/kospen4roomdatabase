package com.example.intel.kospenmove02.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.GenderConverter.Gender;
import com.example.intel.kospenmove02.db.Kospenuser;
import com.example.intel.kospenmove02.db.Screening;

import java.util.Calendar;
import java.util.Date;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Kospenuser addKospenuser(final AppDatabase db, final String id, Date timestamp,
                                      final String ic, final String name, Gender gender,
                                      final String address, final String userRegion,
                                      final String firstRegRegion) {

        Kospenuser kospenuser = new Kospenuser(
                id, timestamp, ic, name, gender, address, userRegion, firstRegRegion);
        db.kospenuserModel().insertKospenuser(kospenuser);

        return kospenuser;
    }

    private static Screening addScreening(final AppDatabase db, final String id, final String fk_ic,
                                      Date date, final int weight, final int height,
                                      final int systolic, final int diastolic,
                                      final int dxt, boolean smoker,
                                     boolean sendToServer) {

        Screening screening = new Screening(
                id, fk_ic, date, weight, height, systolic, diastolic, dxt, smoker, sendToServer);
        db.screeningModel().insertScreening(screening);

        return screening;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.kospenuserModel().deleteAll();
        db.screeningModel().deleteAll();

        Date today = getTodayPlusDays(0);

        Kospenuser user1 = addKospenuser(db, "1", today, "880601105149", "patrick",
                Gender.MALE,"132jlntamarind", "klang", "klang");
        Kospenuser user2 = addKospenuser(db, "2", today, "880601105150", "esther",
                Gender.FEMALE,"132jlntamarind", "klang", "klang");
        addKospenuser(db, "3", today, "880601105155", "romeo",
                Gender.MALE,"132jlntamarind", "klang", "klang");

        Screening screening1 = addScreening(db, "1", "880601105149", today,
                60, 160, 120, 80, 6,
                false, false);
        Screening screening2 = addScreening(db, "1", "880601105155", today,
                20, 80, 90, 60, 6,
                true, true);

    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
