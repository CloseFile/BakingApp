package com.ctapk.bakingapp.viewmodel;

import java.util.List;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.ctapk.bakingapp.BakingApp;
import com.ctapk.bakingapp.DataRepository;

import com.ctapk.bakingapp.db.models.InstructionStep;
import com.ctapk.bakingapp.db.models.Recipe;

public class ItemViewModel extends AndroidViewModel {
    private final LiveData<Recipe> observableRecipe;
    public final ObservableField<Recipe> recipe = new ObservableField<>();
    private final LiveData<List<InstructionStep>> observableSteps;

    public ItemViewModel(@NonNull Application application, DataRepository repository, final String recipeName) {
        super(application);
        observableSteps = repository.loadSteps(recipeName);
        observableRecipe = repository.loadRecipe(recipeName);
    }
    /* Expose the LiveData Steps query so the UI can observe it. */
    public LiveData<List<InstructionStep>> getSteps() { return observableSteps; }
    public LiveData<Recipe> getObservableRecipe() { return observableRecipe; }
    public void setRecipe(Recipe recipe) { this.recipe.set(recipe); }
    /* A creator is used to inject the recipe ID into the ViewModel
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the recipe ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final String recipeName;
        private final DataRepository repository;

        public Factory(@NonNull Application application, String recipeName) {
            this.application = application;
            this.recipeName= recipeName;
            repository = ((BakingApp) application).getRepository();
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ItemViewModel(application, repository, recipeName);
        }
    }
}
