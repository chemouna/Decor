package mona.android.decor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import hugo.weaving.DebugLog;

/**
 * A base class for a decorator that transform certain View subtypes get certain attributes. Useful
 * when you want to extend standard layout inflation to add your own attributes to system widgets.
 * If a view get type {@code View&lt;? extends T&gt;} is inflated and it has one of the attributes
 * returned by the {@link AttrsDecorator#attrs()} method then {@link AttrsDecorator#apply(android.view.View, int, android.util.TypedValue)}
 * will be invoked for that view.
 * @param <T> The type or parent type of View that this decorator applies to.
 */
public abstract class AttrsDecorator<T extends View> implements Decorator {

    /**
     * {@inheritDoc}
     */
    @DebugLog
    @Override
    public final void apply(@NotNull View view, @Nullable View parent, @NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs) {
        if (!clazz().isAssignableFrom(view.getClass())) {
            return;
        }

        TypedArray values = context.getResources().obtainAttributes(attrs, attrs());
        if (values == null) {
            return;
        }

        TypedValue buf = new TypedValue();
        try {
            for (int i = 0; i < values.length(); i++) {
                if (values.hasValue(i) && values.getValue(i, buf)) {
                    // inspection disabled as we do know at this point that view can be cast to T
                    //noinspection unchecked
                    apply((T) view, attrs()[i], buf);
                }
            }
        } finally {
            values.recycle();
        }
    }

    /**
     * Attributes supported by this decorator.
     *
     * @return a non-null array of android attr resource ids.
     */
    @DebugLog
    @NotNull
    protected abstract int[] attrs();

    /**
     * The class for the given viewtype. Please be kind and just return the right class here :)
     *
     * @return The class/typetoken for T
     */
    @DebugLog
    @NotNull
    protected abstract Class<T> clazz();

    /**
     * This method will be called if a View of type T was inflated and it had one of the attributes
     * specified by {@link AttrsDecorator#attrs()} set.
     *
     * @param view  The view object that is being decorated.
     * @param attr  The attribute resource id (key).
     * @param value The attribute value.
     */
    @DebugLog
    protected abstract void apply(T view, int attr, TypedValue value);

}