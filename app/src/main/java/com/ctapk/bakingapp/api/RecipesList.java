package com.ctapk.bakingapp.api;

import com.ctapk.bakingapp.db.entity.RecipeEntity;

import java.util.List;

public class RecipesList {
    private List<RecipeEntity> recipes;
    public List<RecipeEntity> getList() { return recipes; }
}
