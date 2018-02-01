package com.example.intel.kospenmove02;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.GenderConverter;
import com.example.intel.kospenmove02.db.Kospenuser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.intel.kospenmove02.db.GenderConverter.Gender;
import com.example.intel.kospenmove02.db.KospenuserGlobal;
import com.example.intel.kospenmove02.db.KospenuserServer;
import com.example.intel.kospenmove02.db.LocalityConverter;
import com.example.intel.kospenmove02.db.RegionConverter;
import com.example.intel.kospenmove02.db.StateConverter;
import com.example.intel.kospenmove02.db.StateConverter.State;
import com.example.intel.kospenmove02.db.RegionConverter.Region;
import com.example.intel.kospenmove02.db.SubregionConverter;
import com.example.intel.kospenmove02.db.SubregionConverter.Subregion;
import com.example.intel.kospenmove02.db.LocalityConverter.Locality;

public class TestSyncActivity extends AppCompatActivity {

    private Button returnmainButton;
    private Button getApiButton;
    private Button postApiButton;
    private Button showKospenuserserverButton;
    private TextView apiresTextView;

    private static final String TEST_SYNC_TAG = "TestSyncService";
    private String kospenusersUrl = "http://192.168.10.10/api/kospenusers";
    private String kospenusersInsideLocalityUrl = "http://192.168.10.10/api/kospenusers/insidelocality";
    private String kospenusersOusideLocalityUrl = "http://192.168.10.10/api/kospenusers/outsidelocality";

    private AppDatabase mDb;

    private LiveData<List<Kospenuser>> kospenusers;
    private LiveData<List<KospenuserServer>> kospenusersServer;
    private LiveData<List<KospenuserGlobal>> kospenusersGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sync);

        returnmainButton = (Button) findViewById(R.id.returnMainBtnId);
        getApiButton = (Button) findViewById(R.id.getButtonId);
        postApiButton = (Button) findViewById(R.id.postButtonId);
        apiresTextView = (TextView) findViewById(R.id.apiresultTextviewId);
        showKospenuserserverButton = (Button) findViewById(R.id.kospenuserserverButtonId);

        mDb = AppDatabase.getDatabase(this);
    }

    public void returnmainButtonClicked(View view) {
        Intent gotoMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivityIntent);
    }

    public void kospenuserserverButtonClicked(View view) {
        kospenusersServer =  mDb.kospenuserServerModel().loadAllKospenusersServer();
        kospenusersServer.observe(this,
                new Observer<List<KospenuserServer>>() {
                    @Override
                    public void onChanged(@Nullable List<KospenuserServer> kospenusersServer) {
                        showKospenuserServerInUi(kospenusersServer);
                    }
                });
    }

    private void showKospenuserServerInUi(final @NonNull List<KospenuserServer> kospenusersServer) {
        StringBuilder sb = new StringBuilder();

        for (KospenuserServer kospenuserServer : kospenusersServer) {
            sb.append(kospenuserServer.getName());
            sb.append("_");
            sb.append(kospenuserServer.getGender());
            sb.append("_");
            sb.append(kospenuserServer.getTimestamp());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void kospenuserglobalButtonClicked(View view) {
        kospenusersGlobal =  mDb.kospenuserGlobalModel().loadAllKospenusersGlobal();
        kospenusersGlobal.observe(this,
                new Observer<List<KospenuserGlobal>>() {
                    @Override
                    public void onChanged(@Nullable List<KospenuserGlobal> kospenusersGlobal) {
                        showKospenuserGlobalInUi(kospenusersGlobal);
                    }
                });
    }

    private void showKospenuserGlobalInUi(final @NonNull List<KospenuserGlobal> kospenusersGlobal) {
        StringBuilder sb = new StringBuilder();

        for (KospenuserGlobal kospenuserGlobal : kospenusersGlobal) {
            sb.append(kospenuserGlobal.getName());
            sb.append("_");
            sb.append(kospenuserGlobal.getState());
            sb.append("_");
            sb.append(kospenuserGlobal.getTimestamp());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void getButtonClicked(View view) throws Exception {
        // ========== JsonArrayRequest - GET version 2.0 ==========
        // ----------------------------------------------------------------------------------------- <getOutsideLocalityRequest>
        Map<String, String> paramsOutsideLocalityReq = new HashMap<>();
        paramsOutsideLocalityReq.put("state", "PAHANG");
        paramsOutsideLocalityReq.put("region", "MARAN");
        paramsOutsideLocalityReq.put("subregion", "JENGKA2");
        paramsOutsideLocalityReq.put("locality", "ULUJEMPOL");
        JSONObject jsonObjOutsideLocalityReq = new JSONObject(paramsOutsideLocalityReq);

        MyJsonArrayRequest getOutsideLocalityRequest = new MyJsonArrayRequest(
                Request.Method.POST,
                kospenusersOusideLocalityUrl,
                jsonObjOutsideLocalityReq,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful Outside-Locality-Request GET");
//                        apiresTextView.setText(response.toString());
                        try {
                            mDb.kospenuserGlobalModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                State state = StateConverter.strToEnumState(jsonObject.getString("state"));
                                Region region = RegionConverter.strToEnumRegion(jsonObject.getString("region"));
                                Subregion subregion = SubregionConverter.strToEnumSubregion(jsonObject.getString("subregion"));
                                Locality locality = LocalityConverter.strToEnumLocality(jsonObject.getString("locality"));
                                KospenuserGlobal kospenuserGlobal = new KospenuserGlobal(updated_at, ic, name,
                                        state, region, subregion, locality);
                                mDb.kospenuserGlobalModel().insertKospenuserGlobal(kospenuserGlobal);
                            }

                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserGlobal row insert Failed, error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                        apiresTextView.setText(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };


        // ----------------------------------------------------------------------------------------- <getInsideLocalityRequest>
        Map<String, String> paramsInsideLocalityReq = new HashMap<>();
        paramsInsideLocalityReq.put("state", "PAHANG");
        paramsInsideLocalityReq.put("region", "MARAN");
        paramsInsideLocalityReq.put("subregion", "JENGKA2");
        paramsInsideLocalityReq.put("locality", "ULUJEMPOL");
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
//                        apiresTextView.setText(response.toString());
                        try {
                            apiresTextView.setText(response.getJSONObject(0).getString("name"));

                            mDb.kospenuserServerModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                Gender gender = GenderConverter.strToEnumGender(jsonObject.getString("gender"));
                                String address = jsonObject.getString("address");
                                State state = StateConverter.strToEnumState(jsonObject.getString("state"));
                                Region region = RegionConverter.strToEnumRegion(jsonObject.getString("region"));
                                Subregion subregion = SubregionConverter.strToEnumSubregion(jsonObject.getString("subregion"));
                                Locality locality = LocalityConverter.strToEnumLocality(jsonObject.getString("locality"));
                                String firstRegRegion = jsonObject.getString("firstRegRegion");
                                KospenuserServer kospenuserServer = new KospenuserServer(updated_at, ic, name, gender, address,
                                        state, region, subregion, locality, firstRegRegion);
                                mDb.kospenuserServerModel().insertKospenuserServer(kospenuserServer);
                            }

                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserServer row insert Failed, error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                        apiresTextView.setText(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

//        // 'getInsideLocalityRequest' -> Version 1:
//        JsonArrayRequest getInsideLocalityRequest = new JsonArrayRequest(
//                Request.Method.POST,
//                kospenusersInsideLocalityUrl,
//                parameters,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray  response) {
//                        Log.i(TEST_SYNC_TAG, "Successful GET");
////                        apiresTextView.setText(response.toString());
//                        try {
//                            apiresTextView.setText(response.getJSONObject(0).getString("name"));
//
//                            mDb.kospenuserServerModel().deleteAll();
//
//                            for (int i=0;i<response.length();i++) {
//                                //Inserting a new kospenuser row into sqlite
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                String updated_at = jsonObject.getString("updated_at");
//                                String ic = jsonObject.getString("ic");
//                                String name = jsonObject.getString("name");
//                                Gender gender = GenderConverter.strToEnumGender(jsonObject.getString("gender"));
//                                String address = jsonObject.getString("address");
//                                State state = StateConverter.strToEnumState(jsonObject.getString("state"));
//                                Region region = RegionConverter.strToEnumRegion(jsonObject.getString("region"));
//                                Subregion subregion = SubregionConverter.strToEnumSubregion(jsonObject.getString("subregion"));
//                                Locality locality = LocalityConverter.strToEnumLocality(jsonObject.getString("locality"));
//                                String firstRegRegion = jsonObject.getString("firstRegRegion");
//                                KospenuserServer kospenuserServer = new KospenuserServer(updated_at, ic, name, gender, address,
//                                        state, region, subregion, locality, firstRegRegion);
//                                mDb.kospenuserServerModel().insertKospenuserServer(kospenuserServer);
//                            }
//
//                        } catch (Exception e){
//                            Log.e(TEST_SYNC_TAG, "KospenuserServer row insert Failed, error: " + e.getMessage());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.toString());
//                        apiresTextView.setText(error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding GET JsonArrayRequest to queue to send request to remote server");
        MySingleton.getInstance(this).addToRequestQueue(getInsideLocalityRequest);
        MySingleton.getInstance(this).addToRequestQueue(getOutsideLocalityRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] GET JsonArrayRequest sent");
    }

    public void postButtonClicked(View view) {
        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "[doInBackground] Preparing JsonObjectRequest");
        Map<String, String> params = new HashMap<>();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
        String timestamp = LocalDateTime.now().format(df);

        params.put("ic", "881201105315");
        params.put("created_at", timestamp);
        params.put("updated_at", timestamp);
        params.put("name", "bellio2");
        params.put("gender", "MALE");
        params.put("address", "SouthernPark");
        params.put("state", "NONPAHANG");
        params.put("region", "JERANTUT");
        params.put("subregion", "JENGKA2");
        params.put("locality", "ULUJEMPOL");
        params.put("firstRegRegion", "klang");

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                kospenusersUrl,
                parameters,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful POST: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed POST: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest to queue to send request to remote server");
        MySingleton.getInstance(this).addToRequestQueue(postRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest sent");
    }
}
