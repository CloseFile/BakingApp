package com.ctapk.bakingapp.ui;

import com.ctapk.bakingapp.db.models.Recipe;


public interface RecipeClickCallback {
    void onClick(Recipe recipe);
}
