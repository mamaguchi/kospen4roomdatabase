package com.example.intel.kospenmove02.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.intel.kospenmove02.db.entity.Kospenuser;
import com.example.intel.kospenmove02.db.entity.KospenuserServer;
import com.example.intel.kospenmove02.db.entity.Screening;
import com.example.intel.kospenmove02.db.utils.DatabaseInitializer;

import java.util.List;


public class ViewModel extends AndroidViewModel {

//    public final LiveData<List<Kospenuser>> kospenusers;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioOne;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioTwo;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioThree;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioFourA;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioFourB;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioFourC;
//    public final LiveData<List<Kospenuser>> kospenusersScenarioFive;
//    public final LiveData<List<KospenuserServer>> kospenusersScenarioSix;
    public final LiveData<List<Screening>> screenings;

    private AppDatabase mDb;

    public ViewModel(Application application) {
        super(application);

        createDb();

        // 'kospenusers' & 'screenings' are LiveData object,
        // so updates are observed.
//        kospenusers = mDb.kospenuserModel().loadAllKospenusers();
//        kospenusersScenarioOne = mDb.kospenuserModel().loadScenarioOne();
//        kospenusersScenarioTwo = mDb.kospenuserModel().loadScenarioTwo();
//        kospenusersScenarioThree = mDb.kospenuserModel().loadScenarioThree();
//        kospenusersScenarioFourA = mDb.kospenuserModel().loadScenarioFourA();
//        kospenusersScenarioFourB = mDb.kospenuserModel().loadScenarioFourB();
//        kospenusersScenarioFourC = mDb.kospenuserModel().loadScenarioFourC();
//        kospenusersScenarioFive = mDb.kospenuserModel().loadScenarioFive();
//        kospenusersScenarioSix = mDb.kospenuserServerModel().loadScenarioSix();

        screenings = mDb.screeningModel().loadAllScreenings();
    }

    public void createDb() {
        mDb = AppDatabase.getDatabase(this.getApplication());

        // Populate it with initial data
//        DatabaseInitializer.populateAsync(mDb);
    }
}
