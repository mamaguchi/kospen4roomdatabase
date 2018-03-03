package com.example.intel.kospenmove02;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.entity.InDBQueryKospenuser;
import com.example.intel.kospenmove02.db.entity.Kospenuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.intel.kospenmove02.db.entity.KospenuserGlobal;
import com.example.intel.kospenmove02.db.entity.KospenuserServer;
import com.example.intel.kospenmove02.db.converter.OutRestReqConverter.OutRestReq;
import com.example.intel.kospenmove02.db.converter.InDBQueryConverter.InDBQuery;
import com.example.intel.kospenmove02.db.entity.OutRestReqKospenuser;
import com.example.intel.kospenmove02.db.viewmodel.TestActivityViewModel;

public class TestSyncActivity extends AppCompatActivity {

    private Button returnmainButton;
    private Button getApiButton;
    private Button postApiButton;
    private Button showKospenuserserverButton;

    private TextView apiresTextView;
    private TextView InDBOutReqTableTextView;

    private static final String TEST_SYNC_TAG = "TestSyncService";
    private String kospenusersUrl = "http://192.168.10.10/api/kospenusers";
    private String kospenusersInsideLocalityUrl = "http://192.168.10.11/api/kospenusers/insidelocality";
    private String kospenusersOusideLocalityUrl = "http://192.168.10.11/api/kospenusers/outsidelocality";
    private String outRestReqKospenuserUrl = "http://192.168.10.11/api/kospenusers/testoutrestreq";

    private AppDatabase mDb;

    private TestActivityViewModel mTestActivityViewModel;

    private LiveData<List<Kospenuser>> kospenusers;
    private LiveData<List<KospenuserServer>> kospenusersServer;
    private LiveData<List<KospenuserGlobal>> kospenusersGlobal;
    private LiveData<List<Kospenuser>> kospenusersScenarioOne;
    private LiveData<List<Kospenuser>> kospenusersScenarioTwo;
    private LiveData<List<Kospenuser>> kospenusersScenarioThree;
    private LiveData<List<Kospenuser>> kospenusersScenarioFourA;
    private LiveData<List<Kospenuser>> kospenusersScenarioFourB;
    private LiveData<List<Kospenuser>> kospenusersScenarioFourC;
    private LiveData<List<Kospenuser>> kospenusersScenarioFive;
    private LiveData<List<KospenuserServer>> kospenusersScenarioSix;
    private LiveData<List<OutRestReqKospenuser>> outRestReqKospenusers;
    private LiveData<List<InDBQueryKospenuser>> inDBQueryKospenusers;

    // --------------------------------------------------------------------------------------------------------------
    // [Activity On-Create]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sync);

        returnmainButton = (Button) findViewById(R.id.returnMainBtnId);
        getApiButton = (Button) findViewById(R.id.getButtonId);
        postApiButton = (Button) findViewById(R.id.postButtonId);

        apiresTextView = (TextView) findViewById(R.id.apiresultTextviewId);
        InDBOutReqTableTextView = (TextView) findViewById(R.id.inDBOutReqTableTextViewId);

        showKospenuserserverButton = (Button) findViewById(R.id.kospenuserserverButtonId);

        mDb = AppDatabase.getDatabase(this);

        mTestActivityViewModel = ViewModelProviders.of(this).get(TestActivityViewModel.class);
    }

    public void returnmainButtonClicked(View view) {
        Intent gotoMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivityIntent);
    }

    // --------------------------------------------------------------------------------------------------------------
    // [Display InDB-OutReq Table]
    private void showOutRestReqKospenuserInUi(final @NonNull List<OutRestReqKospenuser> outRestReqKospenusers) {
        StringBuilder sb = new StringBuilder();

        for (OutRestReqKospenuser outRestReqKospenuser : outRestReqKospenusers) {
            sb.append(outRestReqKospenuser.getName());
            sb.append("_Ic:");
            sb.append(outRestReqKospenuser.getIc());
            sb.append("_Status:");
            sb.append(outRestReqKospenuser.getOutRestReqStatus());
            sb.append("_FailCounter:");
            sb.append(outRestReqKospenuser.getOutRestReqFailCounter());
            sb.append("\n");
        }
        InDBOutReqTableTextView.setText(sb.toString());
    }

    private void showInDBQueryKospenuserInUi(final @NonNull List<InDBQueryKospenuser> inDBQueryKospenusers) {
        StringBuilder sb = new StringBuilder();

        for (InDBQueryKospenuser inDBQueryKospenuser : inDBQueryKospenusers) {
            sb.append(inDBQueryKospenuser.getName());
            sb.append("_");
            sb.append(inDBQueryKospenuser.getIc());
            sb.append("_");
            sb.append(inDBQueryKospenuser.getInDBQueryStatus());
            sb.append("\n");
        }
        InDBOutReqTableTextView.setText(sb.toString());
    }

    // --------------------------------------------------------------------------------------------------------------
    // [Create InDB-OutReq Table]
    public void clearInDBOutReqTableBtnClicked(View view) {
        mDb.inDBQueryKospenuserModel().deleteAll();
        mDb.outRestReqKospenuserModel().deleteAll();
    }

    public void scene1Clicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioOne =  mDb.kospenuserModel().loadScenarioOne();
//        kospenusersScenarioOne.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene1InUi(kospenusers);
//                    }
//                });

//        outRestReqKospenusers = mDb.outRestReqKospenuserModel().loadAllOutRestReqKospenusers();
//        outRestReqKospenusers.observe(this,
//                new Observer<List<OutRestReqKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<OutRestReqKospenuser> outRestReqKospenusers) {
//                        showOutRestReqKospenuserInUi(outRestReqKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioOne();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmLocal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void showScene1InUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmLocal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene2Clicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioTwo =  mDb.kospenuserModel().loadScenarioTwo();
//        kospenusersScenarioTwo.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene2InUi(kospenusers);
//                    }
//                });

//        inDBQueryKospenusers = mDb.inDBQueryKospenuserModel().loadAllInDBQueryKospenusers();
//        inDBQueryKospenusers.observe(this,
//                new Observer<List<InDBQueryKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<InDBQueryKospenuser> inDBQueryKospenusers) {
//                        showInDBQueryKospenuserInUi(inDBQueryKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
//        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioTwo();
//        for (Kospenuser kospenuser : kospenusers) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.LocalKospenuserUpdateFrmInsideLocality);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 3: Modified 'loadScenarioTwo()' to apply changes to local sqlite DB immediately.
        mDb.kospenuserModel().loadScenarioTwo();
    }

    private void showScene2InUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.LocalKospenuserUpdateFrmInsideLocality);
            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene3Clicked(View view) {
        mDb.kospenuserModel().loadScenarioThree();

//        kospenusersScenarioThree =  mDb.kospenuserModel().loadScenarioThree();
//        kospenusersScenarioThree.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene3InUi(kospenusers);
//                    }
//                });
    }

    private void showScene3InUi(final @NonNull List<Kospenuser> kospenusers) {
        StringBuilder sb = new StringBuilder();

        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene4aClicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioFourA =  mDb.kospenuserModel().loadScenarioFourA();
//        kospenusersScenarioFourA.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene4aInUi(kospenusers);
//                    }
//                });

//        outRestReqKospenusers = mDb.outRestReqKospenuserModel().loadAllOutRestReqKospenusers();
//        outRestReqKospenusers.observe(this,
//                new Observer<List<OutRestReqKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<OutRestReqKospenuser> outRestReqKospenusers) {
//                        showOutRestReqKospenuserInUi(outRestReqKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourA();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

    }

    private void showScene4aInUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    // Need to delete this kospenuser from Android(local) Kospenuser.java entity
    public void scene4bClicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioFourB =  mDb.kospenuserModel().loadScenarioFourB();
//        kospenusersScenarioFourB.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene4bInUi(kospenusers);
//                    }
//                });

//        inDBQueryKospenusers = mDb.inDBQueryKospenuserModel().loadAllInDBQueryKospenusers();
//        inDBQueryKospenusers.observe(this,
//                new Observer<List<InDBQueryKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<InDBQueryKospenuser> inDBQueryKospenusers) {
//                        showInDBQueryKospenuserInUi(inDBQueryKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
//        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourB();
//        for (Kospenuser kospenuser : kospenusers) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.LocalKospenuserUpdateFrmOutsideLocality);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 3: Modified 'loadScenarioFourB()' to apply changes to local sqlite DB immediately.
        mDb.kospenuserModel().loadScenarioFourB();

    }

    private void showScene4bInUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuser);
            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.LocalKospenuserUpdateFrmOutsideLocality);
            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene4cClicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioFourC =  mDb.kospenuserModel().loadScenarioFourC();
//        kospenusersScenarioFourC.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene4cInUi(kospenusers);
//                    }
//                });

//        outRestReqKospenusers = mDb.outRestReqKospenuserModel().loadAllOutRestReqKospenusers();
//        outRestReqKospenusers.observe(this,
//                new Observer<List<OutRestReqKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<OutRestReqKospenuser> outRestReqKospenusers) {
//                        showOutRestReqKospenuserInUi(outRestReqKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFourC();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }
    }

    private void showScene4cInUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerFrmGlobal);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene5Clicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioFive =  mDb.kospenuserModel().loadScenarioFive();
//        kospenusersScenarioFive.observe(this,
//                new Observer<List<Kospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
//                        showScene5InUi(kospenusers);
//                    }
//                });

//        outRestReqKospenusers = mDb.outRestReqKospenuserModel().loadAllOutRestReqKospenusers();
//        outRestReqKospenusers.observe(this,
//                new Observer<List<OutRestReqKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<OutRestReqKospenuser> outRestReqKospenusers) {
//                        showOutRestReqKospenuserInUi(outRestReqKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
        List<Kospenuser> kospenusers = mDb.kospenuserModel().loadScenarioFive();
        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerNewKospenuser);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

    }

    private void showScene5InUi(final @NonNull List<Kospenuser> kospenusers) {

        for (Kospenuser kospenuser : kospenusers) {
            OutRestReqKospenuser outRestReqKospenuser = new OutRestReqKospenuser(kospenuser);
            outRestReqKospenuser.setOutRestReqStatus(OutRestReq.UpdateServerNewKospenuser);
            mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(outRestReqKospenuser.getIc());
            mDb.outRestReqKospenuserModel().insertOutRestReqKospenuser(outRestReqKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_");
            sb.append(kospenuser.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    public void scene6Clicked(View view) {
        // VERSION 1: using LiveData -> good for testing sql statements to see if desired data is return by Android sqlite
//        kospenusersScenarioSix =  mDb.kospenuserServerModel().loadScenarioSix();
//        kospenusersScenarioSix.observe(this,
//                new Observer<List<KospenuserServer>>() {
//                    @Override
//                    public void onChanged(@Nullable List<KospenuserServer> kospenusersServer) {
//                        showScene6InUi(kospenusersServer);
//                    }
//                });

//        inDBQueryKospenusers = mDb.inDBQueryKospenuserModel().loadAllInDBQueryKospenusers();
//        inDBQueryKospenusers.observe(this,
//                new Observer<List<InDBQueryKospenuser>>() {
//                    @Override
//                    public void onChanged(@Nullable List<InDBQueryKospenuser> inDBQueryKospenusers) {
//                        showInDBQueryKospenuserInUi(inDBQueryKospenusers);
//                    }
//                });

        // VERSION 2: using normal java list -> when LiveData is no longer required to update UI
//        List<KospenuserServer> kospenusersServer = mDb.kospenuserServerModel().loadScenarioSix();
//        for (KospenuserServer kospenuserServer : kospenusersServer) {
//            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuserServer);
//            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.KospenuserInsideLocalityNotInAndroidDb);
//            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
//            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
//        }

        // VERSION 3: Modified 'loadScenarioSix()' to apply changes to local sqlite DB immediately. [LONG FORM]
//        mDb.query("INSERT into kospenuser(timestamp, ic, name, address, fk_gender, fk_state, fk_region, fk_subregion, fk_locality, firstRegRegion, version, " +
//                "softDel, dirty) " +
//                "SELECT * from kospenuserserver WHERE " +
//                "ic NOT in (SELECT ic from kospenuser)", null);
//        cursor.getCount();

        // VERSION 4: Modified 'loadScenarioSix()' to apply changes to local sqlite DB immediately. [SHORT FORM]
        Cursor cursor =  mDb.query("INSERT INTO kospenuser SELECT * from kospenuserserver WHERE " +
                "ic NOT in (SELECT ic from kospenuser)", null);
        cursor.getCount();
    }

    private void showScene6InUi(final @NonNull List<KospenuserServer> kospenusersServer) {

        for (KospenuserServer kospenuserServer : kospenusersServer) {
            InDBQueryKospenuser inDBQueryKospenuser = new InDBQueryKospenuser(kospenuserServer);
            inDBQueryKospenuser.setInDBQueryStatus(InDBQuery.KospenuserInsideLocalityNotInAndroidDb);
            mDb.inDBQueryKospenuserModel().deleteInDBQueryKospenuserByIc(inDBQueryKospenuser.getIc());
            mDb.inDBQueryKospenuserModel().insertInDBQueryKospenuser(inDBQueryKospenuser);
        }

        StringBuilder sb = new StringBuilder();
        for (KospenuserServer kospenuserServer : kospenusersServer) {
            sb.append(kospenuserServer.getName());
            sb.append("_");
            sb.append(kospenuserServer.getIc());
            sb.append("\n");
        }
        apiresTextView.setText(sb.toString());
    }

    // --------------------------------------------------------------------------------------------------------------<AndroidDB manipulation>
    // [Show Local Kospenuser List in UI]
    public void localdbButtonClicked(View view) {
        kospenusers =  mDb.kospenuserModel().loadAllKospenusers();
        kospenusers.observe(this,
                new Observer<List<Kospenuser>>() {
                    @Override
                    public void onChanged(@Nullable List<Kospenuser> kospenusers) {
                        showLocalKospenusersInUi(kospenusers);
                    }
                });
    }

    // [Display Local Kospenuser List]
    private void showLocalKospenusersInUi(final @NonNull List<Kospenuser> kospenusers) {
        StringBuilder sb = new StringBuilder();

        for (Kospenuser kospenuser : kospenusers) {
            sb.append(kospenuser.getName());
            sb.append("_Addr:");
            sb.append(kospenuser.getAddress());
            sb.append("_Date:");
            sb.append(kospenuser.getTimestamp());
            sb.append("_Version:");
            sb.append(kospenuser.getVersion());
            sb.append("_Dirty:");
            sb.append(kospenuser.isDirty());
            sb.append("_SoftDel:");
            sb.append(kospenuser.isSoftDel());
            sb.append("\n");
        }
        InDBOutReqTableTextView.setText(sb.toString());
    }

    // [Show OutRestReqKospenuser List in UI]
    public void outrestreqButtonClicked(View view) {
        outRestReqKospenusers =  mDb.outRestReqKospenuserModel().loadAllOutRestReqKospenusers();
        outRestReqKospenusers.observe(this,
                new Observer<List<OutRestReqKospenuser>>() {
                    @Override
                    public void onChanged(@Nullable List<OutRestReqKospenuser> outRestReqKospenusers) {
                        showOutRestReqKospenuserInUi(outRestReqKospenusers);
                    }
                });
    }

    // [Show InDBQueryKospenuser List in UI]
    public void indbqueryButtonClicked(View view) {
        inDBQueryKospenusers = mDb.inDBQueryKospenuserModel().loadAllInDBQueryKospenusers();
        inDBQueryKospenusers.observe(this,
                new Observer<List<InDBQueryKospenuser>>() {
                    @Override
                    public void onChanged(@Nullable List<InDBQueryKospenuser> inDBQueryKospenusers) {
                        showInDBQueryKospenuserInUi(inDBQueryKospenusers);
                    }
                });
    }

    // [Show KospenuserServer List in UI]
    public void kospenuserserverButtonClicked(View view) {
        kospenusersServer =  mDb.kospenuserServerModel().loadAllKospenusersServer();
        kospenusersServer.observe(this,
                new Observer<List<KospenuserServer>>() {
                    @Override
                    public void onChanged(@Nullable List<KospenuserServer> kospenusersServer) {
                        showKospenuserServerInUi(kospenusersServer);
                    }
                });
    }

    private void showKospenuserServerInUi(final @NonNull List<KospenuserServer> kospenusersServer) {
        StringBuilder sb = new StringBuilder();

        for (KospenuserServer kospenuserServer : kospenusersServer) {
            sb.append(kospenuserServer.getName());
            sb.append("_");
            sb.append(kospenuserServer.getFk_gender());
            sb.append("_");
            sb.append(kospenuserServer.getTimestamp());
            sb.append("\n");
        }
        InDBOutReqTableTextView.setText(sb.toString());
    }

    // [Show KospenuserGlobal List in UI]
    public void kospenuserglobalButtonClicked(View view) {
        kospenusersGlobal =  mDb.kospenuserGlobalModel().loadAllKospenusersGlobal();
        kospenusersGlobal.observe(this,
                new Observer<List<KospenuserGlobal>>() {
                    @Override
                    public void onChanged(@Nullable List<KospenuserGlobal> kospenusersGlobal) {
                        showKospenuserGlobalInUi(kospenusersGlobal);
                    }
                });
    }

    private void showKospenuserGlobalInUi(final @NonNull List<KospenuserGlobal> kospenusersGlobal) {
        StringBuilder sb = new StringBuilder();

        for (KospenuserGlobal kospenuserGlobal : kospenusersGlobal) {
            sb.append(kospenuserGlobal.getName());
            sb.append("_");
            sb.append(kospenuserGlobal.getFk_state());
            sb.append("_");
            sb.append(kospenuserGlobal.getTimestamp());
            sb.append("\n");
        }
        InDBOutReqTableTextView.setText(sb.toString());
    }


    // --------------------------------------------------------------------------------------------------------------<REST api interection>
    // [Get a list of Inside-locality & Outside-locality Kospenusers.
    // Create KospenuserGlobal-Table & KospenuserServer-Table in Android-Sqlite from Inside-Locality & Outside-Locality]
    public void getButtonClicked(View view) throws Exception {
        // ========== JsonArrayRequest - GET version 2.0 ==========
        // ----------------------------------------------------------------------------------------- <getOutsideLocalityRequest>
        // !!! TODO-ATTENTION !!! //
        // THESE PART IS STILL HARDCODED.
        // THE QUERY PARAMS NEED TO BE ABLE
        // TO CAPTURE THE LOCATION OF THE JURURAWAT-MASYARAKAT AUTOMATICALLY
        Map<String, Integer> paramsOutsideLocalityReq = new HashMap<>();
        paramsOutsideLocalityReq.put("state", 1);
        paramsOutsideLocalityReq.put("region", 1);
        paramsOutsideLocalityReq.put("subregion", 1);
        paramsOutsideLocalityReq.put("locality", 1);
        JSONObject jsonObjOutsideLocalityReqPayload = new JSONObject(paramsOutsideLocalityReq);

        MyJsonArrayRequest getOutsideLocalityRequest = new MyJsonArrayRequest(
                Request.Method.POST,
                kospenusersOusideLocalityUrl,
                jsonObjOutsideLocalityReqPayload,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful Outside-Locality-Request GET");
//                        apiresTextView.setText(response.toString());
                        try {
                            mDb.kospenuserGlobalModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                int gender = jsonObject.getInt("gender");
                                String address = jsonObject.getString("address");
                                int state = jsonObject.getInt("state");
                                int region = jsonObject.getInt("region");
                                int subregion = jsonObject.getInt("subregion");
                                int locality = jsonObject.getInt("locality");
                                String firstRegRegion = jsonObject.getString("firstRegRegion");
                                int version = jsonObject.getInt("version");
                                KospenuserGlobal kospenuserGlobal = new KospenuserGlobal(updated_at, ic, name, address, gender,
                                        state, region, subregion, locality, firstRegRegion, version);
                                mDb.kospenuserGlobalModel().insertKospenuserGlobal(kospenuserGlobal);
                            }

                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserGlobal row insert Failed, error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                        apiresTextView.setText(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };


        // ----------------------------------------------------------------------------------------- <getInsideLocalityRequest>
        // !!! TODO-ATTENTION !!! //
        // THESE PART IS STILL HARDCODED.
        // THE QUERY PARAMS NEED TO BE ABLE
        // TO CAPTURE THE LOCATION OF THE JURURAWAT-MASYARAKAT AUTOMATICALLY
        Map<String, Integer> paramsInsideLocalityReq = new HashMap<>();
        paramsInsideLocalityReq.put("state", 1);
        paramsInsideLocalityReq.put("region", 1);
        paramsInsideLocalityReq.put("subregion", 1);
        paramsInsideLocalityReq.put("locality", 1);
        JSONObject jsonObjInsideLocalityReq = new JSONObject(paramsInsideLocalityReq);

        // 'getInsideLocalityRequest' -> Version 2:
        MyJsonArrayRequest getInsideLocalityRequest = new MyJsonArrayRequest(
                Request.Method.POST,
                kospenusersInsideLocalityUrl,
                jsonObjInsideLocalityReq,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        Log.i(TEST_SYNC_TAG, "Successful Inside-Locality-Request GET");
//                        apiresTextView.setText(response.toString());
                        try {
                            apiresTextView.setText(response.getJSONObject(0).getString("name"));

                            mDb.kospenuserServerModel().deleteAll();

                            for (int i=0;i<response.length();i++) {
                                //Inserting a new kospenuser row into sqlite
                                JSONObject jsonObject = response.getJSONObject(i);
                                String updated_at = jsonObject.getString("updated_at");
                                String ic = jsonObject.getString("ic");
                                String name = jsonObject.getString("name");
                                int gender = jsonObject.getInt("gender");
                                String address = jsonObject.getString("address");
                                int state = jsonObject.getInt("state");
                                int region = jsonObject.getInt("region");
                                int subregion = jsonObject.getInt("subregion");
                                int locality = jsonObject.getInt("locality");
                                String firstRegRegion = jsonObject.getString("firstRegRegion");
                                int version = jsonObject.getInt("version");
                                KospenuserServer kospenuserServer = new KospenuserServer(updated_at, ic, name, address, gender,
                                        state, region, subregion, locality, firstRegRegion, version);
                                mDb.kospenuserServerModel().insertKospenuserServer(kospenuserServer);
                            }

                        } catch (Exception e){
                            Log.e(TEST_SYNC_TAG, "KospenuserServer row insert Failed, error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.getMessage());
                        apiresTextView.setText(error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

//        // 'getInsideLocalityRequest' -> Version 1:
//        JsonArrayRequest getInsideLocalityRequest = new JsonArrayRequest(
//                Request.Method.POST,
//                kospenusersInsideLocalityUrl,
//                parameters,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray  response) {
//                        Log.i(TEST_SYNC_TAG, "Successful GET");
////                        apiresTextView.setText(response.toString());
//                        try {
//                            apiresTextView.setText(response.getJSONObject(0).getString("name"));
//
//                            mDb.kospenuserServerModel().deleteAll();
//
//                            for (int i=0;i<response.length();i++) {
//                                //Inserting a new kospenuser row into sqlite
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                String updated_at = jsonObject.getString("updated_at");
//                                String ic = jsonObject.getString("ic");
//                                String name = jsonObject.getString("name");
//                                Gender gender = GenderConverter.strToEnumGender(jsonObject.getString("gender"));
//                                String address = jsonObject.getString("address");
//                                State state = StateConverter.strToEnumState(jsonObject.getString("state"));
//                                Region region = RegionConverter.strToEnumRegion(jsonObject.getString("region"));
//                                Subregion subregion = SubregionConverter.strToEnumSubregion(jsonObject.getString("subregion"));
//                                Locality locality = LocalityConverter.strToEnumLocality(jsonObject.getString("locality"));
//                                String firstRegRegion = jsonObject.getString("firstRegRegion");
//                                KospenuserServer kospenuserServer = new KospenuserServer(updated_at, ic, name, gender, address,
//                                        state, region, subregion, locality, firstRegRegion);
//                                mDb.kospenuserServerModel().insertKospenuserServer(kospenuserServer);
//                            }
//
//                        } catch (Exception e){
//                            Log.e(TEST_SYNC_TAG, "KospenuserServer row insert Failed, error: " + e.getMessage());
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i(TEST_SYNC_TAG, "Failed GET: " + error.toString());
//                        apiresTextView.setText(error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding GET JsonArrayRequest to queue to send request to remote server");
        MySingleton.getInstance(this).addToRequestQueue(getInsideLocalityRequest);
        MySingleton.getInstance(this).addToRequestQueue(getOutsideLocalityRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] GET JsonArrayRequest sent");
    }

    // [Insert a new Kospenuser into Server DB]
    public void postButtonClicked(View view) {
        // ========== JsonObjectRequest - POST ==========
        Log.i(TEST_SYNC_TAG, "[doInBackground] Preparing JsonObjectRequest");
        Map<String, String> params = new HashMap<>();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
        String timestamp = LocalDateTime.now().format(df);

        params.put("ic", "881201105315");
        params.put("created_at", timestamp);
        params.put("updated_at", timestamp);
        params.put("name", "bellio2");
        params.put("gender", "MALE");
        params.put("address", "SouthernPark");
        params.put("state", "NONPAHANG");
        params.put("region", "JERANTUT");
        params.put("subregion", "JENGKA2");
        params.put("locality", "ULUJEMPOL");
        params.put("firstRegRegion", "klang");

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest postRequest = new JsonObjectRequest(
                Request.Method.POST,
                kospenusersUrl,
                parameters,
                new Response.Listener<JSONObject >() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        Log.i(TEST_SYNC_TAG, "Successful POST: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TEST_SYNC_TAG, "Failed POST: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest to queue to send request to remote server");
        MySingleton.getInstance(this).addToRequestQueue(postRequest);
        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest sent");
    }

    // [Send OutRestReqKospenusers to Server DB]
    public void outreqsyncButtonClicked(View view) {
        mTestActivityViewModel.outRestReqSync();

        // ========== JsonObjectRequest - POST ==========
//        Log.i(TEST_SYNC_TAG, "[doInBackground] Preparing JsonObjectRequest for OutRestReqKospenuser Sync with ServerDB");
//
//        List<OutRestReqKospenuser> outRestReqKospenuserList = mDb.outRestReqKospenuserModel().loadAll();
//        JSONObject jsonObjectPayload = new JSONObject();
//        try{
//            JSONObject jsonObjectTable = new JSONObject();
//            int counter = 1;
//            for (OutRestReqKospenuser outRestReqKospenuser : outRestReqKospenuserList) {
//                JSONObject jsonObjectRow = new JSONObject();
//                jsonObjectRow.put("version", outRestReqKospenuser.getVersion());
//                jsonObjectRow.put("status", outRestReqKospenuser.getOutRestReqStatus());
//                jsonObjectRow.put("ic", outRestReqKospenuser.getIc());
//                jsonObjectRow.put("created_at", outRestReqKospenuser.getTimestamp());
//                jsonObjectRow.put("updated_at", outRestReqKospenuser.getTimestamp());
//                jsonObjectRow.put("name", outRestReqKospenuser.getName());
//                jsonObjectRow.put("gender", outRestReqKospenuser.getFk_gender());
//                jsonObjectRow.put("address", outRestReqKospenuser.getAddress());
//                jsonObjectRow.put("state", outRestReqKospenuser.getFk_state());
//                jsonObjectRow.put("region", outRestReqKospenuser.getFk_region());
//                jsonObjectRow.put("subregion", outRestReqKospenuser.getFk_subregion());
//                jsonObjectRow.put("locality", outRestReqKospenuser.getFk_locality());
//                jsonObjectRow.put("firstRegRegion", outRestReqKospenuser.getFirstRegRegion());
//
//                jsonObjectTable.put("user"+counter, jsonObjectRow);
//                counter += 1;
//            }
//            jsonObjectPayload.put("data", jsonObjectTable);
//
//        } catch (JSONException jse) {
//            Log.e(TEST_SYNC_TAG, jse.getMessage());
//        }
//
//        Log.i(TEST_SYNC_TAG, jsonObjectPayload.toString());
//
//        // Version 1: using 'JsonObjectRequest'
//        JsonObjectRequest postRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                outRestReqKospenuserUrl,
//                jsonObjectPayload,
//                new Response.Listener<JSONObject >() {
//                    @Override
//                    public void onResponse(JSONObject  response) {
//                        Log.i(TEST_SYNC_TAG, "Successful outRestReqKospenuserUrl: \n" + response.toString() + "\n");
//
//                        Iterator<String> keys = response.keys();
//                        while (keys.hasNext()) {
//
//                            try {
//                                JSONObject jsonObjectEachUser = (JSONObject) response.get(keys.next());
//
//                                // Version 1: using 'if'
////                                if (jsonObjectEachUser.getString("returnStatus").equalsIgnoreCase("UPDATE_SUCCESSFUL")) {
////                                    Log.i(TEST_SYNC_TAG, "Successful Update RespObj-> " + jsonObjectEachUser.getString("name"));
////                                } else if (jsonObjectEachUser.getString("returnStatus").equalsIgnoreCase("INSERT_SUCCESSFUL")){
////                                    Log.i(TEST_SYNC_TAG, "Successful Insert RespObj-> " + jsonObjectEachUser.getString("name"));
////                                }// end-if
//
//                                // Version 2: using 'switch'
//                                switch (jsonObjectEachUser.getString("returnStatus")) {
//                                    case "UPDATE_SUCCESSFUL":
//                                        Log.i(TEST_SYNC_TAG, "Successful Update RespObj-> " + jsonObjectEachUser.getString("name") +
//                                                " " + jsonObjectEachUser.getInt("version"));
////                                        mDb.kospenuserModel().updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
////                                        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
//                                        mTestActivityViewModel.updateVersionColKospenuser(jsonObjectEachUser.getInt("version"),jsonObjectEachUser.getString("ic"));
//                                        mTestActivityViewModel.deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
//                                        break;
//                                    case "INSERT_SUCCESSFUL":
//                                        Log.i(TEST_SYNC_TAG, "Successful Insert RespObj-> " + jsonObjectEachUser.getString("name") +
//                                                " " + jsonObjectEachUser.getInt("version"));
//                                        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
//                                        break;
//                                    case "UPDATE_DENIED_STALE_VERSION":
//                                        Log.i(TEST_SYNC_TAG, "Update Denied RespObj-> " + jsonObjectEachUser.getString("name") +
//                                                " " + jsonObjectEachUser.getInt("version"));
////                                        mDb.outRestReqKospenuserModel().deleteOutRestReqKospenuserByIc(jsonObjectEachUser.getString("ic"));
//                                        break;
//                                    default:
//                                        Log.i(TEST_SYNC_TAG, "Unknown returnStatus!");
//                                        break;
//                                }// end-switch
//
//
//                            } catch (JSONException jse) {
//                                Log.i(TEST_SYNC_TAG, jse.getMessage());
//                            }// end-try
//                        }// end-while
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i(TEST_SYNC_TAG, "Failed outRestReqKospenuserUrl: " + error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//        };
//
//
//
//        Log.i(TEST_SYNC_TAG, "[doInBackground] Adding JsonObjectRequest[outRestReqKospenuserUrl] to queue to send request to remote server");
//        MySingleton.getInstance(this).addToRequestQueue(postRequest);
//        Log.i(TEST_SYNC_TAG, "[doInBackground] JsonObjectRequest[outRestReqKospenuserUrl] sent");
    }
}
