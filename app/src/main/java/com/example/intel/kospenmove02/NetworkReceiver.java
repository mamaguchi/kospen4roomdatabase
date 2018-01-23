package com.example.intel.kospenmove02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class NetworkReceiver extends BroadcastReceiver {

//    public static final String WIFI = "Wi-Fi";
//    public static final String ANY = "Any";
//    public static String sPref = null;


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(NetworkActivity.WIFI.equals(NetworkActivity.sPref)
                && networkInfo!=null
                && networkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
            NetworkActivity.syncData = true;
            Toast.makeText(context, "wifi_connected", Toast.LENGTH_SHORT).show();
        } else if(NetworkActivity.ANY.equals(NetworkActivity.sPref)
                && networkInfo!=null) {
            NetworkActivity.syncData = true;
        } else {
            NetworkActivity.syncData = false;
            Toast.makeText(context, "lost_connection", Toast.LENGTH_SHORT).show();
        }

    }
}
