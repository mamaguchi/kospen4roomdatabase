package com.example.intel.kospenmove02.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intel.kospenmove02.DbActivity;
import com.example.intel.kospenmove02.NetworkStatusActivity;
import com.example.intel.kospenmove02.R;
import com.example.intel.kospenmove02.SettingsActivity;
import com.example.intel.kospenmove02.TestSyncActivity;


public class FragmentDebugLauncher extends Fragment {

    //Button
    private Button buttonTestSyncActivity;
    private Button buttonDB;
    private Button buttonPref;
    private Button buttonNetwork;


    public FragmentDebugLauncher() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_debug_launcher, container, false);

        View rootView = initViews(inflater, container);
        initListeners();

        return rootView;
    }


    // To initialize views objects
    private View initViews(LayoutInflater inflater, ViewGroup container) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_debug_launcher, container, false);

        buttonTestSyncActivity = (Button) rootView.findViewById(R.id.testButtonId);
        buttonDB = (Button) rootView.findViewById(R.id.dbButtonId);
        buttonPref = (Button) rootView.findViewById(R.id.prefButtonId);
        buttonNetwork = (Button) rootView.findViewById(R.id.networkButtonId);

        return rootView;
    }

    // To initialize listeners
    private void initListeners() {

        // TestSyncActivity Button-Listener
        buttonTestSyncActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoTestSyncActivityIntent = new Intent(getContext(), TestSyncActivity.class);
                startActivity(gotoTestSyncActivityIntent);

            }
        });

        // DB-Database Button-Listener
        buttonDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoDbActivityIntent = new Intent(getContext(), DbActivity.class);
                startActivity(gotoDbActivityIntent);

            }
        });

        // Preference Button-Listener
        buttonPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoPrefActivityIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(gotoPrefActivityIntent);

            }
        });

        // Network Button-Listener
        buttonNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoNetworkStatusActivityIntent = new Intent(getContext(), NetworkStatusActivity.class);
                startActivity(gotoNetworkStatusActivityIntent);

            }
        });
    }



}
