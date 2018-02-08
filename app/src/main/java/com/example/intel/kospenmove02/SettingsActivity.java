package com.example.intel.kospenmove02;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;


public class SettingsActivity extends Activity {

    public static final String KEY_PREF_SYNC_WIFI = "pref_sync_wifi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


}
