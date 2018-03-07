package com.example.intel.kospenmove02.db.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.widget.EditText;

import com.example.intel.kospenmove02.MyJsonArrayRequest;
import com.example.intel.kospenmove02.MySingleton;
import com.example.intel.kospenmove02.db.AppDatabase;
import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.validator.ValidationHelper;

import java.util.List;


public class NewKospenuserFormViewModel extends AndroidViewModel {

    //AppDatabase
    private final AppDatabase mDb;

    //LiveData
    private LiveData<List<Kospenuser>> liveDataKospenusers;

    //Constructor
    public NewKospenuserFormViewModel(Application application) {
        super(application);

        mDb = AppDatabase.getDatabase(this.getApplication());
    }


    public void insertNewKospenuser(EditText ic, EditText name, EditText address, EditText gender,
                                    EditText state, EditText region, EditText subregion, EditText locality) {

        String inputIc = ic.getText().toString();
        String inputName = name.getText().toString();
        String inputAddress = address.getText().toString();
        int inputGender = ValidationHelper.genderStringToInt(gender.getText().toString());
        int inputState = ValidationHelper.stateStringToInt(state.getText().toString());
        int inputRegion = ValidationHelper.regionStringToInt(region.getText().toString());
        int inputSubregion = ValidationHelper.subregionStringToInt(subregion.getText().toString());
        int inputLocality = ValidationHelper.localityStringToInt(locality.getText().toString());

        String created_at = ValidationHelper.getCurrentDateTime();

        String dummyFirstRegRegion = "Dummy";

        Kospenuser kospenuser = new Kospenuser(created_at, inputIc, inputName, inputAddress, inputGender,
                inputState, inputRegion, inputSubregion, inputLocality, dummyFirstRegRegion, 1);
        mDb.kospenuserModel().insertKospenuser(kospenuser);
    }


    public LiveData<List<Kospenuser>> getLiveDataKospenusers() {
        return mDb.kospenuserModel().loadAllKospenusers();
    }




}
