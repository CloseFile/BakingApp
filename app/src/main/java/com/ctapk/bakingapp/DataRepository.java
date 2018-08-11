package com.ctapk.bakingapp;

import java.util.List;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.ctapk.bakingapp.db.AppDB;

import com.ctapk.bakingapp.db.models.Ingredient;
import com.ctapk.bakingapp.db.models.InstructionStep;
import com.ctapk.bakingapp.db.models.Recipe;

/* Repository handling the work with recipes and steps. */
public class DataRepository {
    private static DataRepository sInstance;
    private final AppDB appDB;
    private final MediatorLiveData<List<Recipe>> observableRecipes;

    private DataRepository(final AppDB database) {
        appDB = database;
        observableRecipes = new MediatorLiveData<>();

        observableRecipes.addSource(appDB.recipesDao().getRecipes(),
                recipes -> {
                    if (appDB.getDatabaseCreated().getValue() != null) {
                        observableRecipes.postValue(recipes);
                    }
                });
    }

    public static DataRepository getInstance(final AppDB database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /* Get the list of recipes from the database and get notified when the data changes. */
    public LiveData<List<Recipe>> getRecipes() {
        return observableRecipes;
    }

    public LiveData<Recipe> loadRecipe(final String recipeName) {
        return appDB.recipesDao().loadByName(recipeName);
    }

//    public LiveData<Recipe> loadRecipe(final String recipeName) {
//        return appDB.recipesDao().loadByName(recipeName);
//    }

    //    public LiveData<List<InstructionStep>> loadSteps(final int recipeId) {
//        return appDB.stepsDao().loadByParentId(recipeId);
//    }
    public LiveData<List<InstructionStep>> loadSteps(String recipeName) {
        return appDB.stepsDao().getInstructionSteps(recipeName);
    }
    public LiveData<List<Ingredient>> getIngredients(String recipeName) {
        return appDB.ingredientsDao().getIngredients(recipeName);
    }
//    public MediatorLiveData<List<Recipe>> getObservableRecipes() {
//        return observableRecipes;
//    }

//    public InstructionStep loadEntity(final int recipeId, final int stepId) {
//        InstructionStep step = new InstructionStep();
//        AppExecutors.getInstance().diskIO().execute(() -> {
//            InstructionStep entity = new InstructionStep();
//            entity = appDB.stepsDao().getByRecipeId(recipeId, stepId);
//        });
//        return step;
//    }

//    public LiveData<InstructionStep> loadStep(String recipeName, final int stepId) {
//        return appDB.stepsDao().getInstructionStep(recipeName, stepId);
//    }
}
