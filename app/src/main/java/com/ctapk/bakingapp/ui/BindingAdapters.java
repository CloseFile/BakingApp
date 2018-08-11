package com.ctapk.bakingapp.ui;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class BindingAdapters {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
//    @BindingAdapter(value = {"imageUrl", "placeholder"})
//    public static void loadImage(ImageView view, String imageUrl, Drawable placeholder) {
//        GlideApp.with(view.getContext())
//                .load(imageUrl)
//                .placeholder(placeholder)
//                .into(view);
//    }

}