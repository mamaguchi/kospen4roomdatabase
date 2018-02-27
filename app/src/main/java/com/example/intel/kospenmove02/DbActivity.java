package com.example.intel.kospenmove02;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.KospenuserServer;
import com.example.intel.kospenmove02.db.Screening;
import com.example.intel.kospenmove02.db.ViewModel;

import java.util.List;

public class DbActivity extends AppCompatActivity {

    private ViewModel mViewModel;

    private TextView mKospenusersListTextView;
    private TextView mKospenusersServerListTextView;
    private TextView mScreeningsListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        mKospenusersListTextView = (TextView) findViewById(R.id.kospenusersTextViewId);
        mKospenusersServerListTextView = (TextView) findViewById(R.id.kospenusersServerTextViewId);
        mScreeningsListTextView = (TextView) findViewById(R.id.screeningsTextViewId);

        mViewModel = ViewModelProviders.of(this).get(ViewModel.class);

        subscribeToKospenusersList();
        subscribeToKospenusersServerList();
        subscribeToScreeningsList();
    }

    private void subscribeToKospenusersList() {
        mViewModel.kospenusers.observe(this,
                new Observer<List<Kospenuser>>() {
                    @Override
                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
                        showKospenusersInUi(kospenusers);
                    }
                });
    }

    private void subscribeToKospenusersServerList() {
        mViewModel.kospenusersScenarioSix.observe(this,
                new Observer<List<KospenuserServer>>() {
                    @Override
                    public void onChanged(@Nullable List<KospenuserServer> kospenusersServer) {
                        showKospenusersServerInUi(kospenusersServer);
                    }
                });
    }

    private void subscribeToScreeningsList() {
        mViewModel.screenings.observe(this,
                new Observer<List<Screening>>() {
                    @Override
                    public void onChanged(@Nullable List<Screening> screenings) {
                        showScreeningsInUi(screenings);
                    }
                });
    }

    private void showKospenusersInUi(final @NonNull List<Kospenuser> kospenusers) {
        StringBuilder sb = new StringBuilder();

        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("_");
//            sb.append(kospenuser.getTimestamp());
            sb.append("\n");
        }
        mKospenusersListTextView.setText(sb.toString());
    }

    private void showKospenusersServerInUi(final @NonNull List<KospenuserServer> kospenusersServer) {
        StringBuilder sb = new StringBuilder();

        for (KospenuserServer kospenuserServer : kospenusersServer) {
            sb.append(kospenuserServer.getName());
            sb.append("_");
            sb.append(kospenuserServer.getIc());
            sb.append("_");
//            sb.append(kospenuser.getTimestamp());
            sb.append("\n");
        }
        mKospenusersServerListTextView.setText(sb.toString());
    }

    private void showScreeningsInUi(final @NonNull List<Screening> screenings) {
        StringBuilder sb = new StringBuilder();

        for (Screening screening : screenings) {
            sb.append(screening.getSystolic());
            sb.append("/");
            sb.append(screening.getDiastolic());
            sb.append("\n");
        }
        mScreeningsListTextView.setText(sb.toString());
    }
}
































