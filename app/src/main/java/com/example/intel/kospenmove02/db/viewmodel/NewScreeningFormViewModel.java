package com.example.intel.kospenmove02.db.viewmodel;

import android.arch.lifecycle.AndroidViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.widget.EditText;

import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.validator.ValidationHelper;

import java.math.BigDecimal;
import java.util.List;

public class NewScreeningFormViewModel extends AndroidViewModel {

    //AppDatabase
    private final AppDatabase mDb;


    //Constructor
    public NewScreeningFormViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
    }


    public void insertNewScreening(EditText ic, EditText weight, EditText height, EditText systolic,
                                    EditText diastolic, EditText dxt, EditText smoker) {

        String inputIc = ic.getText().toString();
        double inputWeight = Double.parseDouble(weight.getText().toString());
        double inputHeight = Double.parseDouble(height.getText().toString());
        int inputSystolic = Integer.parseInt(systolic.getText().toString());
        int inputDiastolic = Integer.parseInt(diastolic.getText().toString());
        double inputDxt = Double.parseDouble(dxt.getText().toString());
        boolean inputSmoker = smoker.getText().toString()=="Yes"? true:false;

        String created_at = ValidationHelper.getCurrentDateTime();

        int dummyId = 1;

        Screening screening = new Screening(dummyId, inputIc, created_at, inputWeight, inputHeight,
                inputSystolic, inputDiastolic, inputDxt, inputSmoker);
        mDb.screeningModel().insertScreening(screening);
    }


    public LiveData<List<Screening>> getLiveDataScreenings() {
        return mDb.screeningModel().loadAllScreenings();
    }

}
