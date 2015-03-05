package com.madisp.pretty.samples;

import android.util.TypedValue;
import android.widget.ImageView;

import com.madisp.pretty.AttrsDecor;

import org.jetbrains.annotations.NotNull;

/**
 * Created by cheikhna on 01/03/2015.
 */
public class ColorFilterDecor extends AttrsDecor<ImageView> {

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[]{R.attr.colorFilter};
    }

    @NotNull
    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, int attr, TypedValue value) {
        view.setColorFilter(value.resourceId);
    }

}
