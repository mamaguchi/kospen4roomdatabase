package com.example.intel.kospenmove02;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText ic;
    private EditText name;
    private EditText address;
    private Spinner birthdayDaySpinner;
    private Spinner birthdayMonthSpinner;
    private Spinner birthdayYearSpinner;
    private Spinner genderSpinner;
    private Button testButton;
    private Button syncGetButton;
    private Button syncPostButton;
    private Button syncDelButton;
    private Button prefButton;
    private Button networkButton;

    private TextView testOutput;

    // Job Schedule ID
    public static final int SYNC_JOB_ID = "SyncJobService".hashCode();
    // REST api endpoint to 'kospenusers'
    private static final String SYNC_KOSPENPATH = "api/kospenusers";
    // To hold a JobInfo instance
    private static JobInfo mJobInfo = null;
    // JobSchedule TAG
    private static final String JOB_SCHEDULE_TAG = "SyncJobService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -------------------------------------------------------------------------------------- Initialization - START
        testOutput = (TextView) findViewById(R.id.testOutputId);
        ic = (EditText) findViewById(R.id.icId);
        name = (EditText) findViewById(R.id.nameId);
        address = (EditText) findViewById(R.id.addressId);
        birthdayDaySpinner = (Spinner) findViewById(R.id.birthdayDayId);
        birthdayMonthSpinner = (Spinner) findViewById(R.id.birthdayMonthId);
        birthdayYearSpinner = (Spinner) findViewById(R.id.birthdayYearId);
        genderSpinner = (Spinner) findViewById(R.id.genderId);
        testButton = (Button) findViewById(R.id.testButtonId);

        /* syncButton to trigger REST Api call */
        syncGetButton = (Button) findViewById(R.id.syncGetButtonId);
        syncPostButton = (Button) findViewById(R.id.syncPostButtonId);
        syncDelButton = (Button) findViewById(R.id.syncDelButtonId);

        /* prefButton to show preference activity */
        prefButton = (Button) findViewById(R.id.prefButtonId);

        /* prefButton to show network-status activity */
        networkButton = (Button) findViewById(R.id.networkButtonId);

        /* Set up job schedule */
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        mJobInfo = jobScheduler.getPendingJob(SYNC_JOB_ID);
//        Log.i(JOB_SCHEDULE_TAG, "[Pre-setup] Number of all pending jobs: " + jobScheduler.getAllPendingJobs().size());
//        Log.i(JOB_SCHEDULE_TAG, "[Pre-setup] All pending jobs: " + jobScheduler.getAllPendingJobs());
//        Log.i(JOB_SCHEDULE_TAG, "...");
//        if(mJobInfo!=null) {
////            Toast.makeText(this, "Job exist: " + mJobInfo.getId(), Toast.LENGTH_SHORT).show();
//            Log.i(JOB_SCHEDULE_TAG, "Job exist: " + mJobInfo.getId());
//            jobScheduler.cancel(mJobInfo.getId());
//            setupJob();
//        } else {
////            Toast.makeText(this, "No job. Creating new...", Toast.LENGTH_SHORT).show();
//            Log.i(JOB_SCHEDULE_TAG, "No job. Creating new...");
//            setupJob();
//        }
        // -------------------------------------------------------------------------------------- Initialization - END

//        printDatabase();
    }


    // =========== Setup JobInfo and Submit job using JobScheduler - START ===========
    public void setupJob() {
        Log.i(JOB_SCHEDULE_TAG, "Entering setupJob()");
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString(SyncTask.SYNC_KOSPENPATH_KEY, SYNC_KOSPENPATH);

        Log.i(JOB_SCHEDULE_TAG, "Creating JobInfo");
        ComponentName serviceName = new ComponentName(this,
                KospenBackupJobService.class);
        JobInfo.Builder jobinfoBuilder = null;
        jobinfoBuilder = new JobInfo.Builder(SYNC_JOB_ID, serviceName);
        /* One-shot job */
//        jobinfoBuilder.setPersisted(true)
//                .setOverrideDeadline(TimeUnit.HOURS.toMillis(10))
//                .setMinimumLatency(TimeUnit.SECONDS.toMillis(1))
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_METERED)
//                .setRequiresDeviceIdle(false)
//                .setExtras(bundle);
        /* Periodic job */
        jobinfoBuilder.setPersisted(true)
                .setPeriodic(TimeUnit.HOURS.toMillis(10))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_METERED)
                .setRequiresDeviceIdle(false)
                .setExtras(bundle);
        Log.i(JOB_SCHEDULE_TAG, "Retrieving JobScheduler");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        mJobInfo = jobinfoBuilder.build();
        Log.i(JOB_SCHEDULE_TAG, "Minimum Period (in seconds): " + (mJobInfo.getMinPeriodMillis()/1000));
        int result = jobScheduler.schedule(mJobInfo);

        Log.i(JOB_SCHEDULE_TAG, "[Post-setup] Number of all pending jobs: " + jobScheduler.getAllPendingJobs().size());
        Log.i(JOB_SCHEDULE_TAG, "[Post-setup] All pending jobs: " + jobScheduler.getAllPendingJobs());

        if(result!= JobScheduler.RESULT_SUCCESS) {

            Log.i(JOB_SCHEDULE_TAG, "Failed job schedule setup!");

        } else if(result==JobScheduler.RESULT_SUCCESS) {

            Log.i(JOB_SCHEDULE_TAG, "Successful job schedule setup!");

        } else {

            Log.i(JOB_SCHEDULE_TAG, "Error in job schedule setup!");

        }
    }
    // =========== Setup JobInfo and Submit job using JobScheduler - END ===========


    // =========== Goto Database Activity - START ===========
    public void dbButtonClicked(View view) {
        Intent gotoDbActivityIntent = new Intent(this, DbActivity.class);
        startActivity(gotoDbActivityIntent);
    }
    // =========== Goto Database Activity - END ===========


    // =========== Goto Network Activity - START ===========
    public void networkButtonClicked(View view) {
        Intent gotoNetworkStatusActivityIntent = new Intent(this, NetworkStatusActivity.class);
        startActivity(gotoNetworkStatusActivityIntent);
    }
    // =========== Goto Network Activity - END ===========


    // =========== Goto Preference Activity - START ===========
    public void prefButtonClicked(View view) {
        Intent gotoPrefActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(gotoPrefActivityIntent);
    }
    // =========== Goto Preference Activity - END ===========


    // =========== Sync Simulation Button - START ===========
    public void syncGetButtonClicked(View view) {
        // ToDo
    }

    public void syncPostButtonClicked(View view) {
        // ToDo
    }
    // =========== Sync Simulation Button - END ===========


    public void testButtonClicked(View view){
        Intent gotoTestSyncActivityIntent = new Intent(this, TestSyncActivity.class);
        startActivity(gotoTestSyncActivityIntent);
    }

    public void addButtonClicked(View view){
        String str_ic = ic.getText().toString();
        String str_name = name.getText().toString();

        String str_birthdayDay = String.valueOf(birthdayDaySpinner.getSelectedItem());
        String str_birthdayMonth = String.valueOf(birthdayMonthSpinner.getSelectedItem());
        String str_birthdayYear = String.valueOf(birthdayYearSpinner.getSelectedItem());
        StringBuilder sb = new StringBuilder();
        sb.append(str_birthdayDay);
        sb.append("-");
        sb.append(str_birthdayMonth);
        sb.append("-");
        sb.append(str_birthdayYear);

        String str_gender = String.valueOf(genderSpinner.getSelectedItem());
        String str_address = address.getText().toString();

        // ToDo

        printDatabase();
    }

    public void printDatabase(){
        // ToDo
    }

    public void deleteButtonClicked(View view){
        // ToDo
        printDatabase();
    }



}




