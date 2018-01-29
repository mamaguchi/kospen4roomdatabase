package com.example.intel.kospenmove02;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestSyncActivity extends AppCompatActivity {

    private Button returnmainButton;
    private Button getApiButton;
    private Button postApiButton;
    private TextView apiresTextView;

    private static final String TEST_SYNC_TAG = "TestSyncService";
    private String kospenusersUrl = "http://192.168.10.10/api/kospenusers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sync);

        returnmainButton = (Button) findViewById(R.id.returnMainBtnId);
        getApiButton = (Button) findViewById(R.id.getButtonId);
        postApiButton = (Button) findViewById(R.id.postButtonId);
        apiresTextView = (TextView) findViewById(R.id.apiresultTextviewId);
    }

    public void returnmainButtonClicked(View view) {
        Intent gotoMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivityIntent);
    }

    public void getButtonClicked(View view) {
        // ========== JsonArrayRequest - GET version 2.0 ==========
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET,
                kospenusersUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful GET");
                        apiresTextView.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.toString());
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
        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding GET JsonArrayRequest to queue to send request to remote server");
        MySingleton.getInstance(this).addToRequestQueue(getRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] GET JsonArrayRequest sent");
    }

    public void postButtonClicked(View view) {
        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "[doInBackground] Preparing JsonObjectRequest");
        Map<String, String> params = new HashMap<>();
        params.put("name", "bellio7");
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                kospenusersUrl,
                parameters,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful GET: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.toString());
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
