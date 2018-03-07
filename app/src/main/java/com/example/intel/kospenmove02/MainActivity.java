package com.example.intel.kospenmove02;

import android.app.DatePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.PersistableBundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.intel.kospenmove02.fragments.FragmentDebugLauncher;
import com.example.intel.kospenmove02.fragments.FragmentOne;
import com.example.intel.kospenmove02.fragments.FragmentThree;
import com.example.intel.kospenmove02.fragments.FragmentTwo;
import com.example.intel.kospenmove02.validator.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

        // App Bar(Toolbar)
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Creating Tabs with TabLayout & ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "FRAG1");
        adapter.addFragment(new FragmentDebugLauncher(), "FRAG2");
        adapter.addFragment(new FragmentThree(), "FRAG3");
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // FAB (FloatingActionButton)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "FAB Favourite clicked", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

    }

    // Creating Tabs: Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    // Creating AppBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //should return true if you have added items to it and want the menu to be displayed.
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                Toast.makeText(this, "Favourite clicked", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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


//    // =========== Goto Database Activity - START ===========
//    public void dbButtonClicked(View view) {
//        Intent gotoDbActivityIntent = new Intent(this, DbActivity.class);
//        startActivity(gotoDbActivityIntent);
//    }
//
//
//    // =========== Goto Network Activity - START ===========
//    public void networkButtonClicked(View view) {
//        Intent gotoNetworkStatusActivityIntent = new Intent(this, NetworkStatusActivity.class);
//        startActivity(gotoNetworkStatusActivityIntent);
//    }
//
//
//    // =========== Goto Preference Activity - START ===========
//    public void prefButtonClicked(View view) {
//        Intent gotoPrefActivityIntent = new Intent(this, SettingsActivity.class);
//        startActivity(gotoPrefActivityIntent);
//    }
//
//
//    // =========== Goto TestSyncActivity - START ===========
//    public void testButtonClicked(View view){
//        Intent gotoTestSyncActivityIntent = new Intent(this, TestSyncActivity.class);
//        startActivity(gotoTestSyncActivityIntent);
//    }


}




