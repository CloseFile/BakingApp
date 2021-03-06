package com.ctapk.bakingapp.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.ctapk.bakingapp.db.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIngredients(List<Ingredient> ingredients);

    @Query("SELECT * FROM ingredients WHERE recipe_name = :recipeName")
    LiveData<List<Ingredient>> getIngredients(String recipeName);

//    @Query("SELECT * FROM ingredients WHERE recipe_name = :recipeName")
//    List<Ingredient> getIngredientList(String recipeName);
}
