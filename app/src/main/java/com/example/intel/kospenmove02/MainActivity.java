package com.example.intel.kospenmove02;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.net.Uri;
import android.util.Log;

import com.example.intel.kospenmove02.accounts.GenericAccountService;

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

    private MyDBHandler dbHandler;

    private TextView testOutput;

    // Authority for sync adapter's content provider
    public static final String AUTHORITY = "com.kospen.kospenusersprovider.provider";
    // Account type, in the form of domain name for sync adapter
    public static final String ACCOUNT_TYPE = "com.example.android.network.sync.basicsyncadapter";
    // Account name
    public static final String ACCOUNT = "dummyaccount";
    // Variable to hold an instance of Account
    Account mAccount;

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

        // Initialization - START
        dbHandler = new MyDBHandler(this, null, null, 1);
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

        /* Create the dummy account */
        CreateSyncAccount(this);

        /* prefButton to show preference activity */
        prefButton = (Button) findViewById(R.id.prefButtonId);

        /* prefButton to show network-status activity */
        networkButton = (Button) findViewById(R.id.networkButtonId);

        /* Set up job schedule */
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        mJobInfo = jobScheduler.getPendingJob(SYNC_JOB_ID);
        Log.i(JOB_SCHEDULE_TAG, "[Pre-setup] Number of all pending jobs: " + jobScheduler.getAllPendingJobs().size());
        Log.i(JOB_SCHEDULE_TAG, "[Pre-setup] All pending jobs: " + jobScheduler.getAllPendingJobs());
        Log.i(JOB_SCHEDULE_TAG, "...");
        if(mJobInfo!=null) {
//            Toast.makeText(this, "Job exist: " + mJobInfo.getId(), Toast.LENGTH_SHORT).show();
            Log.i(JOB_SCHEDULE_TAG, "Job exist: " + mJobInfo.getId());
            jobScheduler.cancel(mJobInfo.getId());
            setupJob();
        } else {
//            Toast.makeText(this, "No job. Creating new...", Toast.LENGTH_SHORT).show();
            Log.i(JOB_SCHEDULE_TAG, "No job. Creating new...");
            setupJob();
        }

        // Initialization - END

        printDatabase();
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


    // =========== Network Activity - START ===========
    public void networkButtonClicked(View view) {
        Intent prefActivityIntent = new Intent(this, NetworkStatusActivity.class);
        startActivity(prefActivityIntent);
    }
    // =========== Network Activity - END ===========


    // =========== Preference Activity - START ===========
    public void prefButtonClicked(View view) {
        Intent prefActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(prefActivityIntent);
    }
    // =========== Preference Activity - END ===========


    // =========== SyncAdapter CRUD Api - START ===========
    /** Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        //Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        Account newAccount = GenericAccountService.GetAccount();
        AccountManager accountManager = (AccountManager) context.getSystemService(
                ACCOUNT_SERVICE);

        if(accountManager.addAccountExplicitly(newAccount, null, null)) {
            ContentResolver.setIsSyncable(newAccount, AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);
            return newAccount;
        } else {
//            Log.d(TAG, "Account exist or error occured in adding account!");
//            throw new NullPointerException("Account exist or error occured in adding account!");
            Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
            return accounts[0];
        }
    }

//    public void syncGetButtonClicked(View view) {
//        Bundle settingsBundle = new Bundle();
//        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//
//        ContentResolver.requestSync(
//                GenericAccountService.GetAccount(),
//                AUTHORITY,
//                settingsBundle);
//    }

    public void syncPostButtonClicked(View view) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        ContentResolver.requestSync(
                GenericAccountService.GetAccount(),
                AUTHORITY,
                settingsBundle);
    }
    // =========== SyncAdapter CRUD Api - END ===========


    // =========== ContentResolver-ContentProvider CRUD Api - START ===========
    public void testButtonClicked(View view){
//        // Test 1 - to test the input-output function of Spinner
//        String birthdayDay = String.valueOf(birthdayDaySpinner.getSelectedItem());
//        String birthdayMonth = String.valueOf(birthdayMonthSpinner.getSelectedItem());
//        String birthdayYear = String.valueOf(birthdayYearSpinner.getSelectedItem());
//        String gender = String.valueOf(genderSpinner.getSelectedItem());
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(birthdayDay);
//        sb.append("-");
//        sb.append(birthdayMonth);
//        sb.append("-");
//        sb.append(birthdayYear);
//
//
//        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
//        // Test 1 - END

        // Test 2 - to check data in Sqlite database
        testOutput.setText("");
        printDatabase();
        // Test 2 - END
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

//        // ====================================
//        //      1-Using SQLiteOpenHelper
//        // ====================================
//        Person person = new Person(str_name);
//        dbHandler.addPerson(person);
//        // ==================================== END


        // ====================================
        //      2-Using ContentProvider
        // ====================================
        Uri newlyInsertedRowUri;
        ContentValues values = new ContentValues();

        values.put(KospenuserContract.Kospenusers.NAME, str_name);

        newlyInsertedRowUri = getContentResolver().insert(
                KospenuserContract.Kospenusers.CONTENT_URI,
                values
        );
        // ==================================== END

        printDatabase();
    }

    public void printDatabase(){
//        // ====================================
//        //      1-Using SQLiteOpenHelper
//        // ====================================
//        String dbString = dbHandler.databaseToString();
//        testOutput.setText(dbString);
//        // ==================================== END

        // ====================================
        //      2-Using ContentProvider
        // ====================================
        String[] projection = {
                KospenuserContract.Kospenusers._ID,
                KospenuserContract.Kospenusers.NAME
        };
        String selection = null;
        String[] selectionArgs = {""}; /**  This defines a one-element String array to contain the selection argument. */
        selectionArgs = null; /**  If the input is the empty string */
        String sortOrder = "_ID ASC";

        @Nullable
        Cursor queryCursor = getContentResolver().query(
                KospenuserContract.Kospenusers.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        int index = queryCursor.getColumnIndex(KospenuserContract.Kospenusers.NAME);
        if(queryCursor != null) {
            if(queryCursor.getCount() < 1) {
                testOutput.setText("Query returns empty result.");
            } else {
                String dbOutput = "";
                StringBuilder sb = new StringBuilder(50);
                while(queryCursor.moveToNext()) {
                    if(queryCursor.getString(index)!=null) {
                        sb.setLength(0);
                        sb.setLength(50);
                        sb.append(queryCursor.getString(index));

                        dbOutput += sb.toString();
                        dbOutput += "\n";
                    }
                }
                testOutput.setText(dbOutput);
            }
        } else {
            testOutput.setText("Null cursor error!");
//            throw new NullPointerException("null cursor error from content provider occured!");
        }
        // ==================================== END

        ic.setText("");
        name.setText("");
        address.setText("");
    }

    public void deleteButtonClicked(View view){
//        // ====================================
//        //      1-Using SQLiteOpenHelper
//        // ====================================
//        dbHandler.deletePerson(name.getText().toString());
//        printDatabase();
//        // ==================================== END

        // ====================================
        //      2-Using ContentProvider
        // ====================================
        int numDeletedRow;
        String selection = null;
        String[] selectionArgs = {""};

        selection = KospenuserContract.Kospenusers.NAME + " = ?";
        selectionArgs[0] = name.getText().toString();

        numDeletedRow = getContentResolver().delete(
                KospenuserContract.Kospenusers.CONTENT_URI,
                selection,
                selectionArgs
        );
        // ==================================== END

        printDatabase();
    }
    // =========== ContentResolver-ContentProvider CRUD Api - END ===========


}




