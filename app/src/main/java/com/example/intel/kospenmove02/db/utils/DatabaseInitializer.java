package com.example.intel.kospenmove02.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.GenderConverter.Gender;
import com.example.intel.kospenmove02.db.StateConverter.State;
import com.example.intel.kospenmove02.db.RegionConverter.Region;
import com.example.intel.kospenmove02.db.SubregionConverter.Subregion;
import com.example.intel.kospenmove02.db.LocalityConverter.Locality;
import com.example.intel.kospenmove02.db.Kospenuser;
import com.example.intel.kospenmove02.db.Screening;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private static Kospenuser addKospenuser(final AppDatabase db, String timestamp,
                                      final String ic, final String name, Gender gender,
                                      final String address, State state, Region region, Subregion subregion,
                                      Locality locality, final String firstRegRegion) {

        Kospenuser kospenuser = new Kospenuser(
                timestamp, ic, name, gender, address,
                state, region, subregion, locality, firstRegRegion);
        db.kospenuserModel().insertKospenuser(kospenuser);

        return kospenuser;
    }

    private static Screening addScreening(final AppDatabase db, final String id, final String fk_ic,
                                          String date, final int weight, final int height,
                                      final int systolic, final int diastolic,
                                      final int dxt, boolean smoker,
                                     boolean sendToServer) {

        Screening screening = new Screening(
                id, fk_ic, date, weight, height, systolic, diastolic, dxt, smoker, sendToServer);
        db.screeningModel().insertScreening(screening);

        return screening;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.screeningModel().deleteAll();
        db.kospenuserModel().deleteAll();

        String today = getTodayPlusDays(0);

        // Version 1:
//        Kospenuser user1 = addKospenuser(db, today, "880601105149", "patrick",
//                Gender.MALE,"132jlntamarind",
//                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
//        Kospenuser user2 = addKospenuser(db, today, "880601105150", "esther",
//                Gender.FEMALE,"132jlntamarind",
//                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.JENGKA6, "klang");
//        addKospenuser(db, today, "880601105155", "romeo",
//                Gender.MALE,"132jlntamarind",
//                State.NONPAHANG, Region.MARAN, Subregion.JENGKA2, Locality.JENGKA6, "klang");

        // Version 2:
        Kospenuser user1 = addKospenuser(db, "2018-01-30 10:00:00", "880601105149", "patrick",
                Gender.MALE,"bandarputeri",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user2 = addKospenuser(db, "2018-01-30 07:00:00", "880601105151", "bellio2",
                Gender.FEMALE,"southernpark",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user3 = addKospenuser(db, "2018-01-30 07:00:00", "880601105152", "esther",
                Gender.FEMALE,"southernpark",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user4 = addKospenuser(db, "2018-01-29 03:00:00", "880601105153", "romeo",
                Gender.MALE,"southernpark",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user5 = addKospenuser(db, "2018-01-30 09:00:00", "880601105157", "bellio5",
                Gender.MALE,"bandarputeri",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user6 = addKospenuser(db, "2018-01-30 07:00:00", "880601105158", "bellio6",
                Gender.MALE,"southernpark",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Kospenuser user7 = addKospenuser(db, "2018-01-30 08:00:00", "880601105159", "bellio7",
                Gender.MALE,"southernpark",
                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
        Screening screening1 = addScreening(db, "1", "880601105149", today,
                60, 160, 120, 80, 6,
                false, false);
        Screening screening2 = addScreening(db, "2", "880601105151", today,
                20, 80, 90, 60, 6,
                true, true);


        // Version 3:
//        Kospenuser user1 = addKospenuser(db, "2018-02-11 09:17:27", "888888105555", "monster",
//                Gender.MALE,"southernpark",
//                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
//        Kospenuser user2 = addKospenuser(db, "2018-02-12 23:55:40", "888888106666", "mongola",
//                Gender.FEMALE,"southernpark",
//                State.PAHANG, Region.MARAN, Subregion.JENGKA2, Locality.ULUJEMPOL,"klang");
//        Screening screening1 = addScreening(db, "1", "888888105555", today,
//                60, 160, 120, 80, 6,
//                false, false);
//        Screening screening2 = addScreening(db, "2", "888888106666", today,
//                20, 80, 90, 60, 6,
//                true, true);

    }

    private static String getTodayPlusDays(int daysAgo) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, daysAgo);
//        return calendar.getTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
        String timestamp = LocalDateTime.now().format(df);
        return timestamp;
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
