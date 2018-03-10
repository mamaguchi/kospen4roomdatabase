package com.example.intel.kospenmove02.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.intel.kospenmove02.R;
import com.example.intel.kospenmove02.fragment.FragmentNewScreeningForm;


public class NewScreeningFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_screening_form);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_new_screening_form);
        if (fragment==null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            FragmentNewScreeningForm fragmentNewScreeningForm = new FragmentNewScreeningForm();
            ft.add(R.id.fragment_container_new_screening_form, fragmentNewScreeningForm);
            ft.commit();
        }
    }
}
