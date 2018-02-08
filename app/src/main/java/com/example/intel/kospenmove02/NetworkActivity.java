package com.example.intel.kospenmove02;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

// ================================
//
//  Module to Check Network Status
//
// ================================
public class NetworkActivity extends Activity {

    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;

    public static boolean syncData = true;
    public static String sPref = null;

    private NetworkReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sPref = sharedPrefs.getString(SettingsActivity.KEY_PREF_SYNC_WIFI, "");

        // Register NetworkReceiver(BroadcastReceiver) to track
        // network connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver != null) {
            this.unregisterReceiver(receiver);
        }
    }

    // Trigger SyncAdapter if Wifi network is available
    public void onStarting() {
//        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
//        sPref = sharedPrefs.getString(SettingsActivity.KEY_PREF_SYNC_WIFI, "");

        updateConnectedFlags();

        if(syncData) {
            triggerSyncAdapter();
        }
    }

    // Check network connection and sets the
    // 'wifiConnected' and 'mobileConnected'
    // boolean variable accordingly.
    public void updateConnectedFlags() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        if(activeNetworkInfo!=null && activeNetworkInfo.isConnected()) {
            wifiConnected = activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

    // Trigger SyncAdapter to run onPerformSync()
    public void triggerSyncAdapter() {
        if(((sPref.equals(ANY)) && (wifiConnected || mobileConnected))
                || ((sPref.equals(WIFI)) && (wifiConnected))) {
            // TO DO: contentResolver.requestSync()
        } else {
            // TO DO: Network Connection not available or user
            // has disabled update synchronization.(2nd scenario unlikely)
        }
    }
}
























