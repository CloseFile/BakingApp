package com.ctapk.bakingapp.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ctapk.bakingapp.db.entity.RecipeEntity;
import com.ctapk.bakingapp.viewmodel.ListViewModel;

import com.ctapk.bakingapp.R;
import com.ctapk.bakingapp.databinding.ListFragmentBinding;

import java.util.List;

public class ListFragment extends Fragment {
    private static final String TAG = ListFragment.class.getSimpleName();
    private static final int DEFAULT_SIZE = 160;
    private RecipeAdapter mRecipeAdapter;
    private ListFragmentBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
        mRecipeAdapter = new RecipeAdapter(recipeClickCallback);
        mBinding.rvList.setAdapter(mRecipeAdapter);
        return mBinding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ListViewModel viewModel=
                ViewModelProviders.of(this).get(ListViewModel.class);
        subscribeUi(viewModel);
    }
    private void subscribeUi(ListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getRecipes().observe(this, new Observer<List<RecipeEntity>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntity> recipes) {
                if (recipes != null) {
                    mBinding.setIsLoading(false);
                    mRecipeAdapter.setRecipeList(recipes);
                } else {
                    mBinding.setIsLoading(true);
                }
                // espresso does not know how to wait for data binding's loop so we execute changes sync.
                mBinding.executePendingBindings();
            }
        });
    }
    private final RecipeClickCallback recipeClickCallback = recipe -> {
        if (ListFragment.this.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            ((MainActivity) ListFragment.this.getActivity()).show(recipe);
        }
    };

    //todo delete
    /* https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
     * calculate number of columns in GridLayoutManager
     */
    private static int calculateColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / DEFAULT_SIZE);
    }
}
