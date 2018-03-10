package com.example.intel.kospenmove02;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.PersistableBundle;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.util.Log;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.utils.DatabaseInitializer;
import com.example.intel.kospenmove02.fragment.FragmentDebugLauncher;
import com.example.intel.kospenmove02.fragment.FragmentHomepage;
import com.example.intel.kospenmove02.fragment.FragmentKospenuserList;
import com.example.intel.kospenmove02.fragment.FragmentOne;
import com.example.intel.kospenmove02.fragment.FragmentScreeningList;

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
//    private static final String TEST_SYNC_TAG = "SyncJobService";
    private static final String TEST_SYNC_TAG = "TestSyncService";

    //ViewPager
    ViewPager mViewPager;

    //AppDatabase
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // App Bar(Toolbar)
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Creating Tabs with TabLayout & ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHomepage(), "FRAG1");
        adapter.addFragment(new FragmentKospenuserList(), "FRAG2");
        adapter.addFragment(new FragmentScreeningList(), "FRAG3");
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // FAB (FloatingActionButton)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "FAB Favourite clicked", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        //------FOR-DEBUGGING-JOB-SCHEDULER-TO_BE_REMOVED_IN_PRODUCTION------//
//        mDb = AppDatabase.getDatabase(this);
//        DatabaseInitializer.populateAsync(mDb);
//
//        // Set Up Job Schedule
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        mJobInfo = jobScheduler.getPendingJob(SYNC_JOB_ID);
//        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [Pre-setup] Number of all pending jobs: " + jobScheduler.getAllPendingJobs().size());
//        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [Pre-setup] All pending jobs: " + jobScheduler.getAllPendingJobs());
//        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] ...");
//        if(mJobInfo!=null) {
//            Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Job exist: " + mJobInfo.getId());
//            jobScheduler.cancel(mJobInfo.getId());
//            setupJob();
//        } else {
//            Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] No job. Creating new...");
//            setupJob();
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            int currentItem;
            switch (bundle.getString("currentItem")) {
                case "tab2":
                    currentItem = 1;
                    break;
                case "tab3":
                    currentItem = 2;
                    break;
                default:
                    currentItem = 0;
            }
            mViewPager.setCurrentItem(currentItem);

//            if (currentItem!=0) {
//                mViewPager.setCurrentItem(currentItem);
//            }
        }
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
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Entering setupJob()");
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString(SyncTask.SYNC_KOSPENPATH_KEY, SYNC_KOSPENPATH);

        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Configuring JobInfo");
        ComponentName serviceName = new ComponentName(this,
                KospenBackupJobService.class);
        JobInfo.Builder jobinfoBuilder = null;
        jobinfoBuilder = new JobInfo.Builder(SYNC_JOB_ID, serviceName);
        /* One-shot job */
        jobinfoBuilder.setPersisted(true)
                .setOverrideDeadline(TimeUnit.SECONDS.toMillis(10))
                .setMinimumLatency(TimeUnit.SECONDS.toMillis(6))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                .setRequiresDeviceIdle(false)
                .setExtras(bundle);

        /* Periodic job */
//        jobinfoBuilder.setPersisted(true)
//                .setPeriodic(TimeUnit.MINUTES.toMillis(15))
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setRequiresDeviceIdle(false)
//                .setExtras(bundle);
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Retrieving JobScheduler");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Building JobInfo");
        mJobInfo = jobinfoBuilder.build();
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Default Minimum Interval (in seconds): " + (mJobInfo.getMinPeriodMillis()/1000));
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Current Minimum Interval (in seconds): " + (mJobInfo.getIntervalMillis()/1000));
        int result = jobScheduler.schedule(mJobInfo);

        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [Post-setup] Number of all pending jobs: " + jobScheduler.getAllPendingJobs().size());
        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [Post-setup] All pending jobs: " + jobScheduler.getAllPendingJobs());

        if(result!= JobScheduler.RESULT_SUCCESS) {

            Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Failed job schedule setup!");

        } else if(result==JobScheduler.RESULT_SUCCESS) {

            Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Successful job schedule setup!");

        } else {

            Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] Error in job schedule setup!");

        }
    }





}




