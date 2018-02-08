package com.example.intel.kospenmove02;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NetworkStatusActivity extends AppCompatActivity {

    private TextView networkStatusText;
    private Button networkStatusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_status);

        networkStatusText = (TextView) findViewById(R.id.networkStatusId);
        networkStatusButton = (Button) findViewById(R.id.networkStatusButtonId);
    }

    public void networkStatusButtonClicked(View view) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        String isNetworkAvailable = String.valueOf(networkInfo.isAvailable());
        String isConnected = String.valueOf(networkInfo.isConnected());
        String networkType = networkInfo.getTypeName();

        StringBuilder sb = new StringBuilder();
        sb.append(isNetworkAvailable);
        sb.append("\n");
        sb.append(isConnected);
        sb.append("\n");
        sb.append(networkType);

        networkStatusText.setText(sb.toString());
    }


}
