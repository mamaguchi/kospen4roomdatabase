package com.example.intel.kospenmove02.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intel.kospenmove02.MyJsonArrayRequest;
import com.example.intel.kospenmove02.MySingleton;
import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.converter.InDBQueryConverter;
import com.example.intel.kospenmove02.db.converter.OutRestReqConverter;
import com.example.intel.kospenmove02.db.entity.InDBQueryKospenuser;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.KospenuserGlobal;
import com.example.intel.kospenmove02.db.entity.KospenuserServer;
import com.example.intel.kospenmove02.db.entity.OutRestReqKospenuser;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.db.utils.DatabaseInitializer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class TestActivityViewModel extends AndroidViewModel {

    private static final String TEST_SYNC_TAG = "TestSyncService";
    private String kospenusersUrl = "http://192.168.10.10/api/kospenusers";
    private String kospenusersInsideLocalityUrl = "http://192.168.10.11/api/kospenusers/insidelocality";
    private String kospenusersOusideLocalityUrl = "http://192.168.10.11/api/kospenusers/outsidelocality";
    private String outRestReqKospenuserUrl = "http://192.168.10.11/api/kospenusers/testoutrestreq";
    private String outRestReqScreeningUrl = "http://192.168.10.11/api/kospenusers/testscreeningsync";

    Map<String,Integer> rowsOnUpdateSuccessful = new HashMap<>();
    ArrayList<String> rowsOnInsertSuccessful = new ArrayList<>();
    List<OutRestReqKospenuser> outRestReqKospenuserList = new ArrayList<>();
    List<Screening> outRestReqScreeningList = new ArrayList<>();

    private boolean insideLocalityGetReqChecker;
    private boolean outsideLocalityGetReqChecker;

    //AppDatabase
    private final AppDatabase mDb;

    //Constructor
    public TestActivityViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
//        mDb.screeningModel().deleteAll();
//        mDb.kospenuserModel().deleteAll();
        DatabaseInitializer.populateAsync(mDb);
    }

    private void updateVersionColKospenuser(int version, String ic) {
        mDb.kospenuserModel().updateVersionColKospenuser(version, ic);
    }

    private void deleteOutRestReqKospenuserByIc(String ic) {
        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(ic);
    }

    private void deleteKospenuserByIc(String ic) {
        mDb.kospenuserModel().deleteKospenuserByIc(ic);
    }

    private void deleteKospenuserServerByIc(String ic) {
        mDb.kospenuserServerModel().deleteKospenuserServerByIc(ic);
    }

    private void updateKospenuser(int version, int fk_gender, int fk_state, int fk_region, int fk_subregion, int fk_locality,
                                  String name, String address, String firstRegRegion, String ic) {
        mDb.kospenuserModel().updateKospenuser(version, fk_gender, fk_state, fk_region, fk_subregion, fk_locality,
        name, address, firstRegRegion, ic);
    }

    private void setDirtyColTrueKospenuser(String ic) {
        mDb.kospenuserModel().setDirtyColTrueKospenuser(ic);
    }

    private void setDirtyColFalseKospenuser(String ic) {
        mDb.kospenuserModel().setDirtyColFalseKospenuser(ic);
    }

//    private static class DeleteOutrestreqAsync extends AsyncTask<Void, Void, Void> {
//
//        private final AppDatabase mDb;
//        private String mIc;
//
//        DeleteOutrestreqAsync(AppDatabase db, String ic) {
//            mDb = db;
//            mIc = ic;
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            deleteOutRestReqKospenuserByIc(mDb, mIc);
//            return null;
//        }
//    }
//
//    private static class UpdateKospenuserVersionColAsync extends AsyncTask<Void, Void, Void> {
//
//        private final AppDatabase mDb;
//        private int mVersion;
//        private String mIc;
//
//        UpdateKospenuserVersionColAsync(AppDatabase db, int version, String ic) {
//            mDb = db;
//            mVersion = version;
//            mIc = ic;
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            updateVersionColKospenuser(mDb, mVersion, mIc);
//            return null;
//        }
//    }

    public void outRestReqSync() {
        getInsideLocalityKospenuserReq();
    }

    private void getInsideLocalityKospenuserReq() {
        // ----------------------------------------------------------------------------------------- <getInsideLocalityRequest>
        // !!! TODO-ATTENTION !!! //
        // THESE PART IS STILL HARDCODED.
        // THE QUERY PARAMS NEED TO BE ABLE
        // TO CAPTURE THE LOCATION OF THE JURURAWAT-MASYARAKAT AUTOMATICALLY
        Map<String, Integer> paramsInsideLocalityReq = new HashMap<>();
        paramsInsideLocalityReq.put("state", 1);
        paramsInsideLocalityReq.put("region", 1);
        paramsInsideLocalityReq.put("subregion", 1);
        paramsInsideLocalityReq.put("locality", 1);
        JSONObject jsonObjInsideLocalityReq = new JSONObject(paramsInsideLocalityReq);

        // 'getInsideLocalityRequest' -> Version 2:
        MyJsonArrayRequest getInsideLocalityRequest = new MyJsonArrayRequest(
                Request.Method.POST,
                kospenusersInsideLocalityUrl,
                jsonObjInsideLocalityReq,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful Inside-Locality-Request GET");
                        try {
                            mDb.kospenuserServerModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                int gender = jsonObject.getInt("gender");
                                String address = jsonObject.getString("address");
                                int state = jsonObject.getInt("state");
                                int region = jsonObject.getInt("region");
                                int subregion = jsonObject.getInt("subregion");
                                int locality = jsonObject.getInt("locality");
                                String firstRegRegion = jsonObject.getString("firstRegRegion");
                                int version = jsonObject.getInt("version");
                                KospenuserServer kospenuserServer = new KospenuserServer(updated_at, ic, name, address, gender,
                                        state, region, subregion, locality, firstRegRegion, version);
                                mDb.kospenuserServerModel().insertKospenuserServer(kospenuserServer);
                            }
                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserServer row insert Failed, error: " + e.getMessage());
                        }

                        getOutsideLocalityKospenuserReq();
                    }// end-method'onResponse'
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding getInsideLocalityKospenuserReq to queue to send request to remote server");
        MySingleton.getInstance(this.getApplication()).addToRequestQueue(getInsideLocalityRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] getInsideLocalityKospenuserReq sent");
    }

    private void getOutsideLocalityKospenuserReq() {
        // ----------------------------------------------------------------------------------------- <getOutsideLocalityRequest>
        // !!! TODO-ATTENTION !!! //
        // THESE PART IS STILL HARDCODED.
        // THE QUERY PARAMS NEED TO BE ABLE
        // TO CAPTURE THE LOCATION OF THE JURURAWAT-MASYARAKAT AUTOMATICALLY
        Map<String, Integer> paramsOutsideLocalityReq = new HashMap<>();
        paramsOutsideLocalityReq.put("state", 1);
        paramsOutsideLocalityReq.put("region", 1);
        paramsOutsideLocalityReq.put("subregion", 1);
        paramsOutsideLocalityReq.put("locality", 1);
        JSONObject jsonObjOutsideLocalityReqPayload = new JSONObject(paramsOutsideLocalityReq);

        MyJsonArrayRequest getOutsideLocalityRequest = new MyJsonArrayRequest(
                Request.Method.POST,
                kospenusersOusideLocalityUrl,
                jsonObjOutsideLocalityReqPayload,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful Outside-Locality-Request GET");

                        try {
                            mDb.kospenuserGlobalModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                int gender = jsonObject.getInt("gender");
                                String address = jsonObject.getString("address");
                                int state = jsonObject.getInt("state");
                                int region = jsonObject.getInt("region");
                                int subregion = jsonObject.getInt("subregion");
                                int locality = jsonObject.getInt("locality");
                                String firstRegRegion = jsonObject.getString("firstRegRegion");
                                int version = jsonObject.getInt("version");
                                KospenuserGlobal kospenuserGlobal = new KospenuserGlobal(updated_at, ic, name, address, gender,
                                        state, region, subregion, locality, firstRegRegion, version);
                                mDb.kospenuserGlobalModel().insertKospenuserGlobal(kospenuserGlobal);
                            }
                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserGlobal row insert Failed, error: " + e.getMessage());
                        }

                        loadScenariosGenerateOutRestReqAndInDBKospenuser();
                    }// end-method'onResponse'
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding getOutsideLocalityKospenuserReq to queue to send request to remote server");
        MySingleton.getInstance(this.getApplication()).addToRequestQueue(getOutsideLocalityRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] getOutsideLocalityKospenuserReq sent");
    }

    private void loadScenario1() {
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioOne();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReqConverter.OutRestReq.UpdateServerFrmLocal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void loadScenario2() {
        // VERSION 1: using normal java list -> when LiveData is no longer required to update UI
//        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioTwo();
//        for (Kospenuser kospenuser : kospenusers) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQueryConverter.InDBQuery.LocalKospenuserUpdateFrmInsideLocality);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 2: Modified 'loadScenarioTwo()' to apply changes to local sqlite DB immediately.
        mDb.kospenuserModel().loadScenarioTwo();
    }

    private void loadScenario3() {
        mDb.kospenuserModel().loadScenarioThree();
    }

    private void loadScenario4a() {
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourA();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReqConverter.OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void loadScenario4b() {
        // VERSION 1: using normal java list -> when LiveData is no longer required to update UI
//        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourB();
//        for (Kospenuser kospenuser : kospenusers) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQueryConverter.InDBQuery.LocalKospenuserUpdateFrmOutsideLocality);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 2: Modified 'loadScenarioFourB()' to apply changes to local sqlite DB immediately.
        mDb.kospenuserModel().loadScenarioFourB();
    }

    private void loadScenario4c() {
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourC();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReqConverter.OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void loadScenario5() {
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFive();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReqConverter.OutRestReq.UpdateServerNewKospenuser);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void loadScenario6() {
        // VERSION 1: using normal java list -> when LiveData is no longer required to update UI
//        List<KospenuserServer> kospenusersServer = mDb.kospenuserServerModel().loadScenarioSix();
//        for (KospenuserServer kospenuserServer : kospenusersServer) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuserServer);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQueryConverter.InDBQuery.KospenuserInsideLocalityNotInAndroidDb);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 2: Modified 'loadScenarioSix()' to apply changes to local sqlite DB immediately. [LONG FORM]
//        mDb.query("INSERT into kospenuser(timestamp, ic, name, address, fk_gender, fk_state, fk_region, fk_subregion, fk_locality, firstRegRegion, version, " +
//                "softDel, dirty) " +
//                "SELECT * from kospenuserserver WHERE " +
//                "ic NOT in (SELECT ic from kospenuser)", null);
//        cursor.getCount();

        // VERSION 3: Modified 'loadScenarioSix()' to apply changes to local sqlite DB immediately. [SHORT FORM]
        Cursor cursor =  mDb.query("INSERT INTO kospenuser SELECT * from kospenuserserver WHERE " +
                "ic NOT in (SELECT ic from kospenuser)", null);
        cursor.getCount();
    }

    private void loadAllOutRestReqKospenuserNoLiveData() {
        outRestReqKospenuserList = mDb.outRestReqKospenuserModel().loadAll();
    }

    private void setKospenuserSoftDelColTrueWith3orMoreFailCounter() {
        mDb.kospenuserModel().setKospenuserSoftDelColTrueWith3orMoreFailCounter();
    }

    private void setDirtyColFalseIfSoftDelColTrueKospenuser() {
        mDb.kospenuserModel().setDirtyColFalseIfSoftDelColTrueKospenuser();
    }

    private void incrementOutRestReqFailCounterKospenuser(String ic) {
        mDb.outRestReqKospenuserModel().incrementOutRestReqFailCounter(ic);
    }

    private void deleteOutRestReqKospenuserWith3orMoreFailCounter() {
        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserWith3orMoreFailCounter();
    }

    private void loadScenariosGenerateOutRestReqAndInDBKospenuser() {
        loadScenario1();
        loadScenario2();
        loadScenario3();
        loadScenario4a();
        loadScenario4b();
        loadScenario4c();
        loadScenario5();
        loadScenario6();

        loadAllOutRestReqKospenuserNoLiveData();
        sendOutRestReqKospenuser();
    }

    private void sendOutRestReqKospenuser() {
        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "\n\n[doInBackground] Preparing JsonObjectRequest for [OutRestReqKospenuser] Sync with ServerDB");
        JSONObject jsonObjectPayload = new JSONObject();
        try{
            JSONObject jsonObjectTable = new JSONObject();
            int counter = 1;
            for (OutRestReqKospenuser outRestReqKospenuser : outRestReqKospenuserList) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("version", outRestReqKospenuser.getVersion());
                jsonObjectRow.put("status", outRestReqKospenuser.getOutRestReqStatus());
                jsonObjectRow.put("ic", outRestReqKospenuser.getIc());
                jsonObjectRow.put("created_at", outRestReqKospenuser.getTimestamp());
                jsonObjectRow.put("updated_at", outRestReqKospenuser.getTimestamp());
                jsonObjectRow.put("name", outRestReqKospenuser.getName());
                jsonObjectRow.put("gender", outRestReqKospenuser.getFk_gender());
                jsonObjectRow.put("address", outRestReqKospenuser.getAddress());
                jsonObjectRow.put("state", outRestReqKospenuser.getFk_state());
                jsonObjectRow.put("region", outRestReqKospenuser.getFk_region());
                jsonObjectRow.put("subregion", outRestReqKospenuser.getFk_subregion());
                jsonObjectRow.put("locality", outRestReqKospenuser.getFk_locality());
                jsonObjectRow.put("firstRegRegion", outRestReqKospenuser.getFirstRegRegion());

                jsonObjectTable.put("user"+counter, jsonObjectRow);
                counter += 1;
            }
            jsonObjectPayload.put("data", jsonObjectTable);

        } catch (JSONException jse) {
            Log.e(TEST_SYNC_TAG, jse.getMessage());
        }

        Log.i(TEST_SYNC_TAG, jsonObjectPayload.toString());

        // Version 1: using 'JsonObjectRequest'
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                outRestReqKospenuserUrl,
                jsonObjectPayload,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful [outRestReqKospenuserUrl]: \n" + response.toString() + "\n");

                        rowsOnUpdateSuccessful.clear();
                        rowsOnInsertSuccessful.clear();

                        Iterator<String> keys = response.keys();// JSONOBJECT-ITERATION-STARTS-HERE //
                        while (keys.hasNext()) {

                            try {
                                JSONObject jsonObjectEachUser = (JSONObject) response.get(keys.next());

                                // Version 2: using 'switch'
                                switch (jsonObjectEachUser.getString("returnStatus")) {

                                    //---------------------------------------------------------------------------------------------<INSERT>
                                    case "INSERT_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Insert Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " Version :" + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

                                        // VERSION 4: rowsOnInsertSuccessful
                                        rowsOnInsertSuccessful.add(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "INSERT_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "INSERT_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_OLDER_DATA_NEED_UPDATE":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj, Need Update-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        updateKospenuser(
                                                jsonObjectEachUser.getInt("version"),
                                                jsonObjectEachUser.getInt("gender"),
                                                jsonObjectEachUser.getInt("state"),
                                                jsonObjectEachUser.getInt("region"),
                                                jsonObjectEachUser.getInt("subregion"),
                                                jsonObjectEachUser.getInt("locality"),
                                                jsonObjectEachUser.getString("name"),
                                                jsonObjectEachUser.getString("address"),
                                                jsonObjectEachUser.getString("firstRegRegion"),
                                                jsonObjectEachUser.getString("ic")
                                        );
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_OLDER_DATA_NEED_DELETE_DIFFREGION":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj, Need Delete-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        deleteKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_SAME":// This Is Quite An Impossible Situation Theoretically.
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_NEWER_UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj,Timestamp Newer Update Successful-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_NEWER_UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj->Timestamp Newer Update Failed " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_UPDATE_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Kospenuser RespObj,Update Error-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;



                                    //---------------------------------------------------------------------------------------------<UPDATE>
                                    case "UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Update Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " Version :" + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

                                        // VERSION 6: rowsOnUpdateSuccessful
                                        rowsOnUpdateSuccessful.put(jsonObjectEachUser.getString("ic"), jsonObjectEachUser.getInt("version"));
                                        break;

                                    case "UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "UPDATE_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_OLDER_DATA_NEED_UPDATE":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        updateKospenuser(
                                                jsonObjectEachUser.getInt("version"),
                                                jsonObjectEachUser.getInt("gender"),
                                                jsonObjectEachUser.getInt("state"),
                                                jsonObjectEachUser.getInt("region"),
                                                jsonObjectEachUser.getInt("subregion"),
                                                jsonObjectEachUser.getInt("locality"),
                                                jsonObjectEachUser.getString("name"),
                                                jsonObjectEachUser.getString("address"),
                                                jsonObjectEachUser.getString("firstRegRegion"),
                                                jsonObjectEachUser.getString("ic")
                                        );
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_OLDER_DATA_NEED_DELETE_DIFFREGION":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        deleteKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_NEWER_UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_NEWER_UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied Kospenuser RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;


                                    //---------------------------------------------------------------------------------------------<DEFAULT>
                                    default:
                                        Log.i(TEST_SYNC_TAG, "Unknown returnStatus!");
                                        break;
                                }// end-switch


                            } catch (JSONException jse) {
                                Log.i(TEST_SYNC_TAG, jse.getMessage());
                            }// end-try
                        }// end-while

                        Log.i(TEST_SYNC_TAG, "OnKospenuser-UpdateSuccesful--> Size of Map :" + rowsOnUpdateSuccessful.size());
                        for (Map.Entry<String,Integer> entry : rowsOnUpdateSuccessful.entrySet()) {
                            Log.i(TEST_SYNC_TAG, "OnKospenuser-UpdateSuccesful--> Ic :" + entry.getKey() + " ,Version :" + entry.getValue());
                        }
                        for (String rowOnInsertSuccessful : rowsOnInsertSuccessful) {
                            Log.i(TEST_SYNC_TAG, "OnKospenuser-InsertSuccesful--> Ic :" + rowOnInsertSuccessful);
                        }

                        setKospenuserSoftDelColTrueWith3orMoreFailCounter();
                        setDirtyColFalseIfSoftDelColTrueKospenuser();
                        deleteOutRestReqKospenuserWith3orMoreFailCounter();
                        loadAllOutRestReqKospenuserNoLiveData();
                        if (!outRestReqKospenuserList.isEmpty()) {
                            sendOutRestReqKospenuser();
                        } else {
                            prepareOutRestReqScreening();
                        }
                    }// end-method'onResponse'
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed [outRestReqKospenuserUrl]: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest [outRestReqKospenuserUrl] to queue to send request to remote server");
        MySingleton.getInstance(this.getApplication()).addToRequestQueue(postRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest [outRestReqKospenuserUrl] sent");

    }

    private void prepareOutRestReqScreening() {
        setScreeningSoftDelColTrueWithSoftDeletedKospenuser();
        loadAllOutRestReqScreeningNoLiveData();
        sendOutRestReqScreening();
    }

    private void deleteScreeningById(int id) {
        mDb.screeningModel().deleteScreeningById(id);
    }

    private void incrementOutRestReqFailCounterScreening(int id) {
        mDb.screeningModel().incrementOutRestReqFailCounter(id);
    }

    private void setScreeningSoftDelColTrueWith3orMoreFailCounter() {
        mDb.screeningModel().setScreeningSoftDelColTrueWith3orMoreFailCounter();
    }

    private void setScreeningSoftDelColTrueWithSoftDeletedKospenuser() {
        mDb.screeningModel().setScreeningSoftDelColTrueWithSoftDeletedKospenuser();
    }

    private void loadAllOutRestReqScreeningNoLiveData() {
        outRestReqScreeningList = mDb.screeningModel().loadAll();
    }

    private void sendOutRestReqScreening() {
        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "\n\n[doInBackground] Preparing JsonObjectRequest for [OutRestReqScreening] Sync with ServerDB");
        JSONObject jsonObjectPayload = new JSONObject();
        try{
            JSONObject jsonObjectTable = new JSONObject();
            int counter = 1;
            for (Screening outRestReqScreening : outRestReqScreeningList) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("id", outRestReqScreening.getId());
                jsonObjectRow.put("ic", outRestReqScreening.getFk_ic());
                jsonObjectRow.put("created_at", outRestReqScreening.getDate());
                jsonObjectRow.put("weight", outRestReqScreening.getWeight());
                jsonObjectRow.put("height", outRestReqScreening.getHeight());
                jsonObjectRow.put("systolic", outRestReqScreening.getSystolic());
                jsonObjectRow.put("diastolic", outRestReqScreening.getDiastolic());
                jsonObjectRow.put("dxt", outRestReqScreening.getDxt());
                jsonObjectRow.put("smoker", outRestReqScreening.isSmoker()? 1:0);

                jsonObjectTable.put("screening"+counter, jsonObjectRow);
                counter += 1;
            }
            jsonObjectPayload.put("data", jsonObjectTable);

        } catch (JSONException jse) {
            Log.e(TEST_SYNC_TAG, jse.getMessage());
        }

        Log.i(TEST_SYNC_TAG, jsonObjectPayload.toString());

        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                outRestReqScreeningUrl,
                jsonObjectPayload,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful [outRestReqScreeningUrl]: \n" + response.toString() + "\n");

                        rowsOnInsertSuccessful.clear();

                        Iterator<String> keys = response.keys();// JSONOBJECT-ITERATION-STARTS-HERE //
                        while (keys.hasNext()) {

                            try {
                                JSONObject jsonObjectEachScreening = (JSONObject) response.get(keys.next());

                                // Version 2: using 'switch'
                                switch (jsonObjectEachScreening.getString("returnStatus")) {

                                    //---------------------------------------------------------------------------------------------<INSERT>
                                    case "INSERT_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Insert Screening RespObj-> " + jsonObjectEachScreening.getString("ic"));

                                        // VERSION 1: mDB
                                        deleteScreeningById(jsonObjectEachScreening.getInt("id"));
                                        rowsOnInsertSuccessful.add(jsonObjectEachScreening.getString("ic"));
                                        break;

                                    case "INSERT_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Screening RespObj, Insert Failed-> " + jsonObjectEachScreening.getString("ic"));

                                        incrementOutRestReqFailCounterScreening(jsonObjectEachScreening.getInt("id"));
                                        break;

                                    case "INSERT_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Insert Denied Screening RespObj, Insert Error-> " + jsonObjectEachScreening.getString("ic"));

                                        incrementOutRestReqFailCounterScreening(jsonObjectEachScreening.getInt("id"));
                                        break;


                                    //---------------------------------------------------------------------------------------------<DEFAULT>
                                    default:
                                        Log.i(TEST_SYNC_TAG, "Unknown returnStatus!");
                                        break;
                                }// end-switch


                            } catch (JSONException jse) {
                                Log.i(TEST_SYNC_TAG, jse.getMessage());
                            }// end-try
                        }// end-while

                        Log.i(TEST_SYNC_TAG, "OnScreening-InsertSuccesful--> Size of Map :" + rowsOnInsertSuccessful.size());
                        for (String rowOnInsertSuccessful : rowsOnInsertSuccessful) {
                            Log.i(TEST_SYNC_TAG, "OnScreening-InsertSuccesful--> Ic :" + rowOnInsertSuccessful);
                        }

                        setScreeningSoftDelColTrueWith3orMoreFailCounter();
                        loadAllOutRestReqScreeningNoLiveData();
                        if (!outRestReqScreeningList.isEmpty()) {
                            sendOutRestReqScreening();
                        }
                    }// end-method'onResponse'
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed [outRestReqScreeningUrl]: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest [outRestReqScreeningUrl] to queue to send request to remote server");
        MySingleton.getInstance(this.getApplication()).addToRequestQueue(postRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest [outRestReqScreeningUrl] sent");
    }



    public void outRestReqSync__BACKUP_ON_7PM_2MARCH2018_SOME_STATEMENTS_MIGHT_BE_OUTDATED_OR_WITHERROR() {

        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "[doInBackground] Preparing JsonObjectRequest for OutRestReqKospenuser Sync with ServerDB");

        List<OutRestReqKospenuser> outRestReqKospenuserList = mDb.outRestReqKospenuserModel().loadAll();
        JSONObject jsonObjectPayload = new JSONObject();
        try{
            JSONObject jsonObjectTable = new JSONObject();
            int counter = 1;
            for (OutRestReqKospenuser outRestReqKospenuser : outRestReqKospenuserList) {
                JSONObject jsonObjectRow = new JSONObject();
                jsonObjectRow.put("version", outRestReqKospenuser.getVersion());
                jsonObjectRow.put("status", outRestReqKospenuser.getOutRestReqStatus());
                jsonObjectRow.put("ic", outRestReqKospenuser.getIc());
                jsonObjectRow.put("created_at", outRestReqKospenuser.getTimestamp());
                jsonObjectRow.put("updated_at", outRestReqKospenuser.getTimestamp());
                jsonObjectRow.put("name", outRestReqKospenuser.getName());
                jsonObjectRow.put("gender", outRestReqKospenuser.getFk_gender());
                jsonObjectRow.put("address", outRestReqKospenuser.getAddress());
                jsonObjectRow.put("state", outRestReqKospenuser.getFk_state());
                jsonObjectRow.put("region", outRestReqKospenuser.getFk_region());
                jsonObjectRow.put("subregion", outRestReqKospenuser.getFk_subregion());
                jsonObjectRow.put("locality", outRestReqKospenuser.getFk_locality());
                jsonObjectRow.put("firstRegRegion", outRestReqKospenuser.getFirstRegRegion());

                jsonObjectTable.put("user"+counter, jsonObjectRow);
                counter += 1;
            }
            jsonObjectPayload.put("data", jsonObjectTable);

        } catch (JSONException jse) {
            Log.e(TEST_SYNC_TAG, jse.getMessage());
        }

        Log.i(TEST_SYNC_TAG, jsonObjectPayload.toString());

        // Version 1: using 'JsonObjectRequest'
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                outRestReqKospenuserUrl,
                jsonObjectPayload,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful outRestReqKospenuserUrl: \n" + response.toString() + "\n");

                        ArrayList<String> rowsToBeDeleted = new ArrayList<String>();
                        rowsOnUpdateSuccessful.clear();
                        rowsOnInsertSuccessful.clear();

                        Iterator<String> keys = response.keys();
                        while (keys.hasNext()) {

                            try {
                                JSONObject jsonObjectEachUser = (JSONObject) response.get(keys.next());

                                // Version 1: using 'if'
//                                if (jsonObjectEachUser.getString("returnStatus").equalsIgnoreCase("UPDATE_SUCCESSFUL")) {
//                                    Log.i(TEST_SYNC_TAG, "Successful Update RespObj-> " + jsonObjectEachUser.getString("name"));
//                                } else if (jsonObjectEachUser.getString("returnStatus").equalsIgnoreCase("INSERT_SUCCESSFUL")){
//                                    Log.i(TEST_SYNC_TAG, "Successful Insert RespObj-> " + jsonObjectEachUser.getString("name"));
//                                }// end-if

                                // Version 2: using 'switch'
                                switch (jsonObjectEachUser.getString("returnStatus")) {

                                    //---------------------------------------------------------------------------------------------<INSERT>
                                    case "INSERT_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Insert RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " Version :" + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

                                        // VERSION 2: rowsToBeDeleted
//                                        rowsToBeDeleted.add(jsonObjectEachUser.getString("ic"));

                                        // VERSION 3: AsyncTask
//                                        DeleteOutrestreqAsync deleteOutrestreqAsyncTaskOnInsert = new DeleteOutrestreqAsync(mDb,jsonObjectEachUser.getString("ic"));
//                                        deleteOutrestreqAsyncTaskOnInsert.execute();

                                        // VERSION 4: rowsOnInsertSuccessful
                                        rowsOnInsertSuccessful.add(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "INSERT_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "INSERT_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_OLDER_DATA_NEED_UPDATE":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        updateKospenuser(
                                                jsonObjectEachUser.getInt("version"),
                                                jsonObjectEachUser.getInt("gender"),
                                                jsonObjectEachUser.getInt("state"),
                                                jsonObjectEachUser.getInt("region"),
                                                jsonObjectEachUser.getInt("subregion"),
                                                jsonObjectEachUser.getInt("locality"),
                                                jsonObjectEachUser.getString("name"),
                                                jsonObjectEachUser.getString("address"),
                                                jsonObjectEachUser.getString("firstRegRegion"),
                                                jsonObjectEachUser.getString("ic")
                                        );
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_OLDER_DATA_NEED_DELETE_DIFFREGION":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        deleteKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_SAME":// This Is Quite An Impossible Situation Theoretically.
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_NEWER_UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_ANDROID_TIMESTAMP_NEWER_UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_INSERT_DENIED_UPDATE_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;



                                    //---------------------------------------------------------------------------------------------<UPDATE>
                                    case "UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Update RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " Version :" + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

                                        // VERSION 2: rowsToBeDeleted
//                                        rowsToBeDeleted.add(jsonObjectEachUser.getString("ic"));

                                        // VERSION 3: mDB-Transaction
//                                        final int version = jsonObjectEachUser.getInt("version");
//                                        final String ic = jsonObjectEachUser.getString("ic");
//                                        mDb.runInTransaction(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                updateVersionColKospenuser(version,ic);
//                                                deleteOutRestReqKospenuserByIc(ic);
//                                            }
//                                        });

                                        // VERSION 4: AsyncTask
//                                        DeleteOutrestreqAsync deleteOutrestreqAsyncTaskOnUpdate = new DeleteOutrestreqAsync(mDb,jsonObjectEachUser.getString("ic"));
//                                        deleteOutrestreqAsyncTaskOnUpdate.execute();
//                                        UpdateKospenuserVersionColAsync updateKospenuserVersionColAsyncTask = new UpdateKospenuserVersionColAsync(mDb,
//                                                jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
//                                        updateKospenuserVersionColAsyncTask.execute();

                                        // VERSION 5: @Delete
//                                        String updated_at = jsonObjectEachUser.getString("updated_at");
//                                        OutRestReqConverter.OutRestReq outRestReqStatus = jsonObjectEachUser.getString("updated_at");
//                                        String ic = jsonObjectEachUser.getString("ic");
//                                        String name = jsonObjectEachUser.getString("name");
//                                        int gender = jsonObjectEachUser.getInt("gender");
//                                        String address = jsonObjectEachUser.getString("address");
//                                        int state = jsonObjectEachUser.getInt("state");
//                                        int region = jsonObjectEachUser.getInt("region");
//                                        int subregion = jsonObjectEachUser.getInt("subregion");
//                                        int locality = jsonObjectEachUser.getInt("locality");
//                                        String firstRegRegion = jsonObjectEachUser.getString("firstRegRegion");
//                                        int version = jsonObjectEachUser.getInt("version");
//                                        OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(updated_at, ic, name, address, gender,
//                                                state, region, subregion, locality, firstRegRegion, version);
//                                        mDb.kospenuserGlobalModel().insertKospenuserGlobal(kospenuserGlobal);

                                        // VERSION 6: rowsOnUpdateSuccessful
                                        rowsOnUpdateSuccessful.put(jsonObjectEachUser.getString("ic"), jsonObjectEachUser.getInt("version"));
                                        break;

                                    case "UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "UPDATE_ERROR":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_OLDER_DATA_NEED_UPDATE":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        updateKospenuser(
                                                jsonObjectEachUser.getInt("version"),
                                                jsonObjectEachUser.getInt("gender"),
                                                jsonObjectEachUser.getInt("state"),
                                                jsonObjectEachUser.getInt("region"),
                                                jsonObjectEachUser.getInt("subregion"),
                                                jsonObjectEachUser.getInt("locality"),
                                                jsonObjectEachUser.getString("name"),
                                                jsonObjectEachUser.getString("address"),
                                                jsonObjectEachUser.getString("firstRegRegion"),
                                                jsonObjectEachUser.getString("ic")
                                        );
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_OLDER_DATA_NEED_DELETE_DIFFREGION":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        deleteKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_NEWER_UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
                                        setDirtyColFalseKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;

                                    case "ON_UPDATE_STALE_VERSION_ANDROID_TIMESTAMP_NEWER_UPDATE_FAILED":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        incrementOutRestReqFailCounterKospenuser(jsonObjectEachUser.getString("ic"));
                                        break;


                                    //---------------------------------------------------------------------------------------------<DEFAULT>
                                    default:
                                        Log.i(TEST_SYNC_TAG, "Unknown returnStatus!");
                                        break;
                                }// end-switch


                            } catch (JSONException jse) {
                                Log.i(TEST_SYNC_TAG, jse.getMessage());
                            }// end-try
                        }// end-while

                        Log.i(TEST_SYNC_TAG, "OnUpdateSuccesful--> Size of Map :" + rowsOnUpdateSuccessful.size());
                        for (Map.Entry<String,Integer> entry : rowsOnUpdateSuccessful.entrySet()) {
//                            updateVersionColKospenuser(entry.getValue(), entry.getKey());
//                            deleteOutRestReqKospenuserByIc(entry.getKey());

                            Log.i(TEST_SYNC_TAG, "OnUpdateSuccesful--> Ic :" + entry.getKey() + " ,Version :" + entry.getValue());
                        }
                        for (String rowOnInsertSuccessful : rowsOnInsertSuccessful) {
//                            deleteOutRestReqKospenuserByIc(rowOnInsertSuccessful);

                            Log.i(TEST_SYNC_TAG, "OnInsertSuccesful--> Ic :" + rowOnInsertSuccessful);
                        }

                    }// end-method'onResponse'
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed outRestReqKospenuserUrl: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest[outRestReqKospenuserUrl] to queue to send request to remote server");
        MySingleton.getInstance(this.getApplication()).addToRequestQueue(postRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest[outRestReqKospenuserUrl] sent");
    }

}
