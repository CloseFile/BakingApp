package com.ctapk.bakingapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ctapk.bakingapp.db.models.InstructionStep;
import com.ctapk.bakingapp.db.models.Recipe;
import com.ctapk.bakingapp.viewmodel.ItemViewModel;

import com.ctapk.bakingapp.R;
import com.ctapk.bakingapp.databinding.StepsFragmentBinding;

import java.util.List;

public class StepsFragment extends Fragment {
    private static final String KEY_RECIPE_ID = "recipe-id";
    public static final String KEY_RECIPE_NAME = "recipe-name";
    private StepsFragmentBinding binding;
    private StepsAdapter stepsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.steps_fragment, container, false);
        // Create and set the adapter for the RecyclerView.
        stepsAdapter = new StepsAdapter(stepClickCallback);
        binding.detailsList.setAdapter(stepsAdapter);
        return binding.getRoot();
    }
    private final StepClickCallback stepClickCallback = step -> {
//                if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).showStep(step);
//                }
   };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String recipeName=getArguments().getString(KEY_RECIPE_NAME);
        getActivity().setTitle(recipeName);

        ItemViewModel.Factory factory = new ItemViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_RECIPE_NAME));
        final ItemViewModel model = ViewModelProviders.of(this, factory)
                .get(ItemViewModel.class);
        binding.setItemViewModel(model);
        subscribeToModel(model);
    }
    private void subscribeToModel(final ItemViewModel model) {
        // Observe recipe data
        model.getObservableRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipeEntity) {
                model.setRecipe(recipeEntity);
            }
        });
        // Observe steps
        model.getSteps().observe(this, new Observer<List<InstructionStep>>() {
            @Override
            public void onChanged(@Nullable List<InstructionStep> steps) {
                if (steps != null) {
                    binding.setIsLoading(false);
                    stepsAdapter.setStepList(steps);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }
    /** Creates recipe fragment for specific recipe ID */
    public static StepsFragment forRecipe(String recipeName) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_RECIPE_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }
}
