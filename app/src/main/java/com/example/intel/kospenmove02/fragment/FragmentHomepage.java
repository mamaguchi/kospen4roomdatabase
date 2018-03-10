package com.example.intel.kospenmove02.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.intel.kospenmove02.DbActivity;
import com.example.intel.kospenmove02.R;
import com.example.intel.kospenmove02.activity.NewKospenuserFormActivity;
import com.example.intel.kospenmove02.activity.NewScreeningFormActivity;
import com.example.intel.kospenmove02.singleton.ViewPagerSingleton;

public class FragmentHomepage extends Fragment {

    //Button
    private Button button_new_kospenuser;
    private Button button_new_screening;


    public FragmentHomepage() {
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
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);

        button_new_kospenuser = (Button) rootView.findViewById(R.id.button_homepage_new_kospenuser);
        button_new_screening = (Button) rootView.findViewById(R.id.button_homepage_new_screening);

        return rootView;
    }

    // To initialize listeners
    private void initListeners() {

        // button_new_kospenuser Button-Listener
        button_new_kospenuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoNewKospenuserFormActivity = new Intent(getContext(), NewKospenuserFormActivity.class);
                startActivity(gotoNewKospenuserFormActivity);

            }
        });

        // button_new_screening Button-Listener
        button_new_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoNewScreeningFormIntent = new Intent(getContext(), NewScreeningFormActivity.class);
                startActivity(gotoNewScreeningFormIntent);

            }
        });

    }


}
