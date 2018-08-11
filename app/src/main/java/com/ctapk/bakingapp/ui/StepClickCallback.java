package com.ctapk.bakingapp.ui;


import com.ctapk.bakingapp.db.models.InstructionStep;

public interface StepClickCallback {
    void onClick(InstructionStep step);
}