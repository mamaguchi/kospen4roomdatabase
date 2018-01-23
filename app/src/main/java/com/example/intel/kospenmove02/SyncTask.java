package com.example.intel.kospenmove02;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SyncTask extends AsyncTask<JobParameters, Void, Result<JobParameters>> {

    private static final String JOB_SCHEDULE_TAG = "SyncJobService";
    public static final String SYNC_KOSPENPATH_KEY = "kospenusers_api_endpoint";
    private static final int NOTIFICATION_ID = "SyncTaskNotification".hashCode();
    private final WeakReference<JobService> jobServiceWeakRef;
    private String kospenusersUrl = "http://192.168.10.10/api/kospenusers";



    public SyncTask(JobService jobService) {
        this.jobServiceWeakRef = new WeakReference<JobService>(jobService);
    }


    @Override
    protected Result<JobParameters> doInBackground(JobParameters... jobParameters) {

        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] Async task running scheduled job...");
        Result<JobParameters> result = new Result<JobParameters>();
        result.jobParams = jobParameters[0];
        

        // ========== JsonArrayRequest - GET version 2.0 ==========
        JsonArrayRequest getRequest = new JsonArrayRequest(
                Request.Method.GET,
                kospenusersUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(JOB_SCHEDULE_TAG, "Successful GET: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(JOB_SCHEDULE_TAG, "Failed GET: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] Adding GET JsonArrayRequest to queue to send request to remote server");
        MySingleton.getInstance(jobServiceWeakRef.get()).addToRequestQueue(getRequest);
        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] GET JsonArrayRequest sent");


        // ========== JsonObjectRequest - POST ==========
        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] Preparing JsonObjectRequest");
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
                        Intent i = new Intent(jobServiceWeakRef.get(), ApiResultActivity.class);
                        i.putExtra("apiResult", "Successful POST: " + response.toString());
                        jobServiceWeakRef.get().startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent i = new Intent(jobServiceWeakRef.get(), ApiResultActivity.class);
                        i.putExtra("apiResult", "Failed POST: " + error.toString());
                        jobServiceWeakRef.get().startActivity(i);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] Adding JsonObjectRequest to queue to send request to remote server");
        MySingleton.getInstance(jobServiceWeakRef.get()).addToRequestQueue(postRequest);
        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] JsonObjectRequest sent");

        Log.i(JOB_SCHEDULE_TAG, "[doInBackground] Async task completed scheduled job...");
        return result;
    }

    @Override
    protected void onPostExecute(Result<JobParameters> jobParametersResult) {

        Log.i(JOB_SCHEDULE_TAG, "[onPostExecute] Async task: calling onPostExecute() \nJob Completed!");
        Toast.makeText(jobServiceWeakRef.get(), "[onPostExecute] AsyncJob finished successfully.", Toast.LENGTH_SHORT).show();

        jobServiceWeakRef.get().jobFinished(jobParametersResult.jobParams, false);

//        if(jobServiceWeakRef!=null && jobServiceWeakRef.get()!=null) {
//            Toast.makeText(jobServiceWeakRef.get(), "AsyncJob finished successfully.", Toast.LENGTH_SHORT).show();
//            jobServiceWeakRef.get().jobFinished(jobParametersResult.jobParams, false);
//        }
    }
}



































