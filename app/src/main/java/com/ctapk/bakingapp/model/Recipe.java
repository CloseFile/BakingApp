package com.ctapk.bakingapp.model;

import com.ctapk.bakingapp.db.entity.IngredientEntity;

import java.util.List;

public interface Recipe {
    Integer getId();
    String getName();
    List<IngredientEntity> getIngredients();
    Integer getServings();
    String getImage();
//    int getId();
//    String getName();
//    String getIngredients();
//    int getServings();
//    String getImage();
}
