package com.ctapk.bakingapp.db;

import java.util.ArrayList;
import java.util.List;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.ctapk.bakingapp.AppExecutors;
import com.ctapk.bakingapp.db.dao.IngredientsDao;
import com.ctapk.bakingapp.db.dao.InstructionStepsDao;
import com.ctapk.bakingapp.db.dao.RecipesDao;

import com.ctapk.bakingapp.db.models.Ingredient;
import com.ctapk.bakingapp.db.models.InstructionStep;
import com.ctapk.bakingapp.db.models.Recipe;

import retrofit2.Call;
import retrofit2.Response;

import com.ctapk.bakingapp.BakingApp;

// @TypeConverters(ListConverter.class)
@Database(entities = {Recipe.class, InstructionStep.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    private static AppDB sInstance;
    @VisibleForTesting
    public static final String DATABASE_NAME = "baking-app";

    public abstract RecipesDao recipesDao();

    public abstract InstructionStepsDao stepsDao();

    public abstract IngredientsDao ingredientsDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDB getInstance(final Context context, final AppExecutors executors) {
        Log.d("TAG", " getInstance");

        if (sInstance == null) {
            synchronized (AppDB.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /* Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDB buildDatabase(final Context appContext, final AppExecutors executors) {
        Log.d("TAG", " buildDatabase");

        return Room.databaseBuilder(appContext, AppDB.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        ArrayList<Recipe> recipes = new ArrayList<>();

                        BakingApp.getApi().getData().enqueue(new retrofit2.Callback<List<Recipe>>() {
                            @Override
                            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                                recipes.addAll(response.body());

                            }

                            @Override
                            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                                //Проверка на ошибку
                                Log.d("TAG", "Error" + t.toString());
                            }
                        });
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            Log.d("TAG", " CreateDB");

                            // Generate the data for pre-population
                            AppDB database = AppDB.getInstance(appContext, executors);

                            insertData(database, recipes);

                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDB database, final List<Recipe> recipes) {

        // Constant for logging
        String TAG = "called AppDB ";
        Log.d(TAG, "insertData");

        database.runInTransaction(() -> {
            database.recipesDao().insertRecipes(recipes);
            for (Recipe recipe : recipes) {
                List<Ingredient> ingredients = recipe.getIngredients();
                for (Ingredient ingredient : ingredients) {
                    ingredient.setRecipeName(recipe.getName());
                }
                database.ingredientsDao().insertIngredients(ingredients);
                List<InstructionStep> instructionSteps = recipe.getSteps();
                for (InstructionStep step : instructionSteps) {
                    step.setRecipeName(recipe.getName());
                }
                database.stepsDao().insertInstructionSteps(instructionSteps);
            }
        });
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return  isDatabaseCreated;
    }
}
