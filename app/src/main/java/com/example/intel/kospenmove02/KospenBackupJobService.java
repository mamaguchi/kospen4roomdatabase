package com.example.intel.kospenmove02;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;


public class KospenBackupJobService extends JobService {

    private static final String TEST_SYNC_TAG = "TestSyncService";
    private SyncTask mJob = null;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {



        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [onStartJob] on start job: " + jobParameters.getJobId());
        if(mJob==null) {
            // VERSION 1: using SyncTask constructor with 'JobService' arg.
//            mJob = new SyncTask(this);

            // VERSION 2: using SyncTask constructor with 'ApplicationContext' & 'JobService' arg.
            mJob = new SyncTask(getApplicationContext(),this);


            mJob.execute(jobParameters);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.i(TEST_SYNC_TAG, "[JOB SCHEDULER] [onStopJob] on stop job: " + jobParameters.getJobId());
        if(mJob!=null) {
            mJob.cancel(true);
            mJob = null;
        }
        return true;
    }
}
