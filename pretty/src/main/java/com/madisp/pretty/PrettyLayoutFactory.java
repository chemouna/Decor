package com.madisp.pretty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A small util class that wraps a layoutinflater and its factory to apply a bunch of decorators.
 */
public class PrettyLayoutFactory extends LayoutFactoryWrapper {
    Pretty pretty;

    public PrettyLayoutFactory(@NotNull LayoutInflater inflater, @Nullable LayoutInflater.Factory2 base, @NotNull Pretty pretty) {
        super(inflater, base);
        this.pretty = pretty;
    }

    @Override
    @Nullable
    protected View onViewCreated(@Nullable View view, @Nullable View parent, @NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs) {
        if (view == null) {
            return null;
        }
        for (Decor d : pretty.getDecors()) {
            d.apply(view, parent, name, context, attrs);
        }
        return view;
    }
}
