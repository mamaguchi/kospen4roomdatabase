package com.example.intel.kospenmove02.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.intel.kospenmove02.db.utils.DatabaseInitializer;

import java.util.List;


public class ViewModel extends AndroidViewModel {

    public final LiveData<List<Kospenuser>> kospenusers;
    public final LiveData<List<Screening>> screenings;

    private AppDatabase mDb;

    public ViewModel(Application application) {
        super(application);

        createDb();

        // 'kospenusers' & 'screenings' are LiveData object,
        // so updates are observed.
        kospenusers = mDb.kospenuserModel().loadAllKospenusers();
        screenings = mDb.screeningModel().loadAllScreenings();
    }

    public void createDb() {
        mDb = AppDatabase.getDatabase(this.getApplication());

        // Populate it with initial data
        DatabaseInitializer.populateAsync(mDb);
    }
}
