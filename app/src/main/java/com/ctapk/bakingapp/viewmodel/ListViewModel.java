package com.ctapk.bakingapp.viewmodel;

import java.util.List;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ctapk.bakingapp.BakingApp;
import com.ctapk.bakingapp.db.models.Recipe;


public class ListViewModel extends AndroidViewModel {
    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Recipe>> observableRecipes;
    public ListViewModel(Application application) {
        super(application);
        observableRecipes = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableRecipes.setValue(null);
        // Livedata
        LiveData<List<Recipe>> recipes =
                ((BakingApp) application).getRepository().getRecipes();
        // observe the changes of the recipes from the database and forward them
        observableRecipes.addSource(recipes, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> value) {
                observableRecipes.setValue(value);
            }
        });
    }
    /* Expose the LiveData Recipes query so the UI can observe it. */
    public LiveData<List<Recipe>> getRecipes() { return observableRecipes; }
}
