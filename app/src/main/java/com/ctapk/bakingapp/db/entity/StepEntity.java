package com.ctapk.bakingapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.ctapk.bakingapp.model.Step;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(
        foreignKeys = @ForeignKey(
                entity = RecipeEntity.class,
                parentColumns = "name",
                childColumns = "recipe_name",
                onDelete = CASCADE),
        indices = {@Index(value = "recipe_name")},
        tableName = "instruction_steps")
public class StepEntity implements Step {

    @PrimaryKey(autoGenerate = true)
    private final Integer dbId;
    @SerializedName("id")
    @ColumnInfo(name = "step_no")
    private final Integer stepNo;
    @SerializedName("shortDescription")
    @ColumnInfo(name = "short_description")
    private final String shortDescription;
    @SerializedName("description")
    private final String description;
    @SerializedName("videoURL")
    @ColumnInfo(name = "video_url")
    private final String videoURL;
    @SerializedName("thumbnailURL")
    @ColumnInfo(name = "thumbnail_url")
    private final String thumbnailURL;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;

    public StepEntity(Integer dbId, Integer stepNo, String shortDescription, String description, String videoURL, String thumbnailURL, String recipeName) {
        this.dbId = dbId;
        this.stepNo = stepNo;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.recipeName = recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public Integer getStepNo() {
        return stepNo;
    }

    @Override
    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public Integer getDbId() {
        return dbId;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getVideoURL() {
        return videoURL;
    }

    @Override
    public String getThumbnailURL() {
        return thumbnailURL;
    }
}
//@Entity(tableName = "steps", indices = {@Index(value = {"recipeId"})})
//public class StepEntity implements Step {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//    private int recipeId;
//    private int stepId;
//    private String shortDescription;
//    private String description;
//    private String videoURL;
//    private String thumbnailURL;
//
//    @Override
//    public int getId() { return id; }
//    public void setId(int id) { this.id = id; }
//    @Override
//    public int getStepId() { return stepId; }
//    public void setStepId(int stepId) { this.stepId = stepId; }
//    @Override
//    public int getRecipeId() {return recipeId; }
//    public void setRecipeId(int recipeId) { this.recipeId = recipeId; }
//    @Override
//    public String getShortDescription() { return shortDescription;}
//    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
//    @Override
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    @Override
//    public String getVideoURL() { return videoURL; }
//    public void setVideoURL(String videoURL) { this.videoURL = videoURL; }
//    @Override
//    public String getThumbnailURL() { return thumbnailURL; }
//    public void setThumbnailURL(String thumbnailURL) { this.thumbnailURL = thumbnailURL; }
//
//    @Ignore
//    public StepEntity() { }
//    public StepEntity(int id, int recipeId, int stepId, String shortDescription, String description,
//                      String videoURL, String thumbnailURL) {
//        this.id = id;
//        this.recipeId = recipeId;
//        this.stepId = stepId;
//        this.shortDescription = shortDescription;
//        this.description = description;
//        this.videoURL = videoURL;
//        this.thumbnailURL = thumbnailURL;
//    }
//    public StepEntity(Step step) {
//        this.id = step.getId();
//        this.recipeId = step.getRecipeId();
//        this.stepId = step.getStepId();
//        this.shortDescription = step.getShortDescription();
//        this.description = step.getDescription();
//        this.videoURL = step.getVideoURL();
//        this.thumbnailURL = step.getThumbnailURL();
//    }
//}