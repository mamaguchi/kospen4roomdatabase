package com.example.intel.kospenmove02.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.db.entity.Gender;
import com.example.intel.kospenmove02.db.entity.Locality;
import com.example.intel.kospenmove02.db.entity.Region;
import com.example.intel.kospenmove02.db.entity.State;
import com.example.intel.kospenmove02.db.entity.Subregion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Kospenuser addKospenuser(final AppDatabase db, String timestamp,
                                      final String ic, final String name, int gender,
                                      final String address, int state, int region, int subregion,
                                            int locality, final String firstRegRegion, int version) {

        Kospenuser kospenuser = new Kospenuser(
                timestamp, ic, name, address, gender,
                state, region, subregion, locality, firstRegRegion, version);
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

    private static void addGender(final AppDatabase db, int id, String genderName) {
        Gender gender = new Gender(id, genderName);
        db.genderModel().insertGender(gender);
    }

    private static void addState(final AppDatabase db, int id, String stateName) {
        State state = new State(id, stateName);
        db.stateModel().insertState(state);
    }

    private static void addRegion(final AppDatabase db, int id, String regionName) {
        Region region = new Region(id, regionName);
        db.regionModel().insertRegion(region);
    }

    private static void addSubregion(final AppDatabase db, int id, String subregionName) {
        Subregion subregion = new Subregion(id, subregionName);
        db.subregionModel().insertSubregion(subregion);
    }

    private static void addLocality(final AppDatabase db, int id, String localityName) {
        Locality locality = new Locality(id, localityName);
        db.localityModel().insertLocality(locality);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.screeningModel().deleteAll();
        db.kospenuserModel().deleteAll();

        String today = getTodayPlusDays(0);

        // Populate Options-Properties
        // 1- Gender
        addGender(db, 1, "MALE");
        addGender(db, 2, "FEMALE");
        // 2 - State
        addState(db, 1, "PAHANG");
        addState(db, 2, "NONPAHANG");
        // 3 - Region
        addRegion(db, 1, "MARAN");
        addRegion(db, 2, "JERANTUT");
        // 4 - Subregion
        addSubregion(db, 1, "JENGKA2");
        addSubregion(db, 2, "MARAN");
        // 5 - Locality
        addLocality(db, 1, "ULUJEMPOL");
        addLocality(db, 2, "JENGKA6");


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
                1,"bandarputeri",
                1, 1, 1, 1,"klang", 1);
        Kospenuser user2 = addKospenuser(db, "2018-01-30 07:00:00", "880601105151", "bellio2",
                2,"southernpark",
                1, 1, 1, 1,"klang", 1);
        Kospenuser user3 = addKospenuser(db, "2018-01-30 07:00:00", "880601105152", "esther",
                2,"southernpark",
                1, 1, 1, 1,"klang", 1);
        db.kospenuserModel().setDirtyColFalseKospenuser("880601105152");
        Kospenuser user4 = addKospenuser(db, "2018-01-29 03:00:00", "880601105153", "romeo",
                1,"southernpark",
                1, 1, 1, 1,"klang", 1);
        Kospenuser user5 = addKospenuser(db, "2018-01-30 09:00:00", "880601105157", "bellio5",
                1,"bandarputeri",
                1, 1, 1, 1,"klang", 1);
        Kospenuser user6 = addKospenuser(db, "2018-01-30 07:00:00", "880601105158", "bellio6",
                1,"southernpark",
                1, 1, 1, 1,"klang", 1);
        Kospenuser user7 = addKospenuser(db, "2018-01-30 08:00:00", "880601105159", "bellio7",
                1,"southernpark",
                1, 1, 1, 1,"klang", 1);
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
