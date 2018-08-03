package com.ctapk.bakingapp.api;

import com.ctapk.bakingapp.db.entity.RecipeEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryApi {
    @GET("baking.json")
    Call<List<RecipeEntity>> getData();
}