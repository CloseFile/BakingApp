package com.ctapk.bakingapp.api;

import com.ctapk.bakingapp.db.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QueryApi {
    @GET("baking.json")
    Call<List<Recipe>> getData();
}