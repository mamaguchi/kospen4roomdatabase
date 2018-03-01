package com.example.intel.kospenmove02.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intel.kospenmove02.MySingleton;
import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.converter.OutRestReqConverter;
import com.example.intel.kospenmove02.db.entity.OutRestReqKospenuser;
import com.example.intel.kospenmove02.db.utils.DatabaseInitializer;

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

    Map<String,Integer> rowsOnUpdateSuccessful = new HashMap<>();
    ArrayList<String> rowsOnInsertSuccessful = new ArrayList<>();

    private final AppDatabase mDb;

    public TestActivityViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
        DatabaseInitializer.populateAsync(mDb);
    }

    private void updateVersionColKospenuser(int version, String ic) {
        mDb.kospenuserModel().updateVersionColKospenuser(version, ic);
    }

    private void deleteOutRestReqKospenuserByIc(String ic) {
        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(ic);
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
                                    case "UPDATE_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Update RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
                                        updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
//                                        deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

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
                                    case "INSERT_SUCCESSFUL":
                                        Log.i(TEST_SYNC_TAG, "Successful Insert RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));

                                        // VERSION 1: mDB
//                                        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));

                                        // VERSION 2: rowsToBeDeleted
//                                        rowsToBeDeleted.add(jsonObjectEachUser.getString("ic"));

                                        // VERSION 3: AsyncTask
//                                        DeleteOutrestreqAsync deleteOutrestreqAsyncTaskOnInsert = new DeleteOutrestreqAsync(mDb,jsonObjectEachUser.getString("ic"));
//                                        deleteOutrestreqAsyncTaskOnInsert.execute();

                                        // VERSION 4: rowsOnInsertSuccessful
                                        rowsOnInsertSuccessful.add(jsonObjectEachUser.getString("ic"));

                                        break;
                                    case "UPDATE_DENIED_STALE_VERSION":
                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
                                                " " + jsonObjectEachUser.getInt("version"));
                                        // VERSION 1: mDB
//                                        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
                                        break;
                                    default:
                                        Log.i(TEST_SYNC_TAG, "Unknown returnStatus!");
                                        break;
                                }// end-switch


                            } catch (JSONException jse) {
                                Log.i(TEST_SYNC_TAG, jse.getMessage());
                            }// end-try
                        }// end-while

//                        for (String row : rowsToBeDeleted) {
//                            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(row);
//                        }


                        Log.i(TEST_SYNC_TAG, "OnUpdateSuccesful--> Size of Map :" + rowsOnUpdateSuccessful.size());
                        for (Map.Entry<String,Integer> entry : rowsOnUpdateSuccessful.entrySet()) {
//                            updateVersionColKospenuser(entry.getValue(), entry.getKey());
                            deleteOutRestReqKospenuserByIc(entry.getKey());

                            Log.i(TEST_SYNC_TAG, "OnUpdateSuccesful--> Ic :" + entry.getKey() + " ,Version :" + entry.getValue());
                        }
                        for (String rowOnInsertSuccessful : rowsOnInsertSuccessful) {
                            deleteOutRestReqKospenuserByIc(rowOnInsertSuccessful);

                            Log.i(TEST_SYNC_TAG, "OnInsertSuccesful--> Ic :" + rowOnInsertSuccessful);
                        }


                    }
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
