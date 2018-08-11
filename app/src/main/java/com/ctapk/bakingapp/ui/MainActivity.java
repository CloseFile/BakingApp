package com.ctapk.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ctapk.bakingapp.R;
import com.ctapk.bakingapp.db.models.InstructionStep;
import com.ctapk.bakingapp.db.models.Recipe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        // Add recipe list fragment if this is first creation
        if (savedInstanceState == null) {
            ListFragment fragment = new ListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, null)//todo tag remove?
                    .commit();
        }
    }
    public void show(Recipe recipe) {
        StepsFragment stepsFragment = StepsFragment.forRecipe(recipe.getName());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("recipe")
                .replace(R.id.fragment_container, stepsFragment, null)
                .commit();
    }
    /** Shows the recipe detail fragment */
    public void showStep(InstructionStep step) {
        MakeFragment makeFragment = MakeFragment.forStep(step);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("step")
                .replace(R.id.fragment_container, makeFragment, null)
                .commit();
    }
//todo if detail? onSaveInstateState recipeId, stepId
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
}
