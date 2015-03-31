package mona.android.decor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import hugo.weaving.DebugLog;

/**
 * A small util class that wraps a layoutinflater and its factory to
 * apply a bunch of decorators.
 */
public class DecoratorLayoutFactory extends LayoutFactoryWrapper {
    Decor decor;

    @DebugLog
    public DecoratorLayoutFactory(@NotNull LayoutInflater inflater, @Nullable LayoutInflater.Factory2 base, @NotNull Decor decor) {
        super(inflater, base);
        this.decor = decor;
    }

    @DebugLog
    @Override
    @Nullable
    protected View onViewCreated(@Nullable View view, @Nullable View parent, @NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs) {
        if (view == null) {
            return null;
        }
        for (Decorator d : decor.getDecorators()) {
            d.apply(view, parent, name, context, attrs);
        }
        return view;
    }
}
