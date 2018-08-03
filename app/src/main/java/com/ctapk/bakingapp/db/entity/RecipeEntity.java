package com.ctapk.bakingapp.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ctapk.bakingapp.model.Recipe;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "recipes")
public class RecipeEntity {

    @SerializedName("id")
    private final Integer id;
    @SerializedName("name")
    @PrimaryKey
    @NonNull
    private final String name;
    @SerializedName("ingredients")
    @Ignore
    private final List<IngredientEntity> ingredients = null;
    @SerializedName("steps")
    @Ignore
    private final List<StepEntity> steps = null;
    @SerializedName("servings")
    private final Integer servings;
    @SerializedName("image")
    private final String image;

    public RecipeEntity(Integer id, @NonNull String name, Integer servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public List<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public List<StepEntity> getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
//@Entity(tableName = "recipes")
//public class RecipeEntity implements Recipe {
//
//    @SerializedName("id")
//    private  Integer id;
//    @SerializedName("name")
//    @PrimaryKey
//    @NonNull
//    private  String name;
//    @SerializedName("ingredients")
//    @Ignore
//    private final List<IngredientEntity> ingredients = null;
//    @SerializedName("steps")
//    @Ignore
//    private final List<StepEntity> steps = null;
//    @SerializedName("servings")
//    private  Integer servings;
//    @SerializedName("image")
//    private  String image;
//
//
////    @PrimaryKey
////    private int id;
////    private String name;
////    private String ingredients;
////    private int servings;
////    private String image;
//
//    @Override
//    public Integer getId() { return id; }
//    public void setId(Integer id) { this.id = id; }
//    @Override
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//    @Override
//    public Integer getServings() { return servings; }
//    public void setServings(Integer servings) { this.servings = servings; }
//    @Override
//    public String getImage() { return image; }
//    public void setImage(String image) { this.image = image; }
//    @Override
//    public List<IngredientEntity> getIngredients() { return  ingredients; }
//    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }
//
//    @Ignore
//    public RecipeEntity() { }
//    public RecipeEntity(int id, String name, String ingredients, int servings, String image) {
//        this.id = id;
//        this.name = name;
//        this.ingredients = ingredients;
//        this.servings = servings;
//        this.image = image;
//    }
//    public RecipeEntity(Recipe recipe) {
//        this.id = recipe.getId();
//        this.name = recipe.getName();
//        this.ingredients = recipe.getIngredients();
//        this.servings = recipe.getServings();
//        this.image = recipe.getImage();
//    }
//}
