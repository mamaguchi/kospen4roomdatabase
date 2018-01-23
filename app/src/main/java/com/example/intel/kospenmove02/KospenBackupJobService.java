package com.example.intel.kospenmove02;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;


public class KospenBackupJobService extends JobService {

    private static final String JOB_SCHEDULE_TAG = "SyncJobService";
    private SyncTask mJob = null;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.i(JOB_SCHEDULE_TAG, "[onStartJob] on start job: " + jobParameters.getJobId());
        if(mJob==null) {
            mJob = new SyncTask(this);
//            Toast.makeText(this, "Starting job...", Toast.LENGTH_SHORT).show();
            mJob.execute(jobParameters);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.i(JOB_SCHEDULE_TAG, "[onStopJob] on stop job: " + jobParameters.getJobId());
        if(mJob!=null) {
            Toast.makeText(this, "Stopping job...", Toast.LENGTH_SHORT).show();
            mJob.cancel(true);
            mJob = null;
        }
        return true;
    }
}
