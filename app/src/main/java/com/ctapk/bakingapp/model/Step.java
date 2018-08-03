package com.ctapk.bakingapp.model;

public interface Step {
    //    int getId();
//    int getRecipeId();
//    int getStepId();
//    String getShortDescription();
//    String getDescription();
//    String getVideoURL();
//    String getThumbnailURL();
    Integer getStepNo();

    String getRecipeName();

    public Integer getDbId();

    public String getShortDescription();

    public String getDescription();

    public String getVideoURL();

    public String getThumbnailURL();
}
