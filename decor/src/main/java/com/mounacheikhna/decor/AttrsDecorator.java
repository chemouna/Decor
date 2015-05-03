package com.mounacheikhna.decor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;

/**
 * A base class for a decorator that transform certain View subtypes with certain attributes. Useful
 * when you want to extend standard layout inflation to add your own attributes to system widgets.
 * If a view with type {@code View&lt;? extends T&gt;} is inflated and it has one of the attributes
 * returned by the {@link AttrsDecorator#attrs()} method then {@link AttrsDecorator#apply(View, DecorValue)}
 * will be invoked for that view.
 * @param <T> The type or parent type of View that this decorator applies to.
 */
public abstract class AttrsDecorator<T extends View> implements Decorator {

    SparseIntArray attributeIndexes;
    TypedArray values;
    DecorValue decorValue;

    public AttrsDecorator() {
        this.attributeIndexes = new SparseIntArray();
    }

    @Override
    public void apply(View view, View parent, String name, Context context, AttributeSet attributeSet) {
        if (!clazz().isAssignableFrom(view.getClass())) {
            return;
        }

        values = obtainAttributes(context, attributeSet);
        //we should be using this public TypedArray obtainStyledAttributes (AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes)
        //instead to apply also default styles and theme
        //TODO: NOP this doesnt work wih styles set int styles.xml which probably means that they dont work through themes too

        /*if (values == null) {
            return;
        }*/

        if(values == null || values.length() == 0) return;

        try {
            for (int i = 0; i < values.length(); i++) {
                TypedValue buf = new TypedValue();
                if (values.hasValue(i) && values.getValue(i, buf)) {
                    attributeIndexes.put(attrs()[i], i);
                }
            }
            if(attributeIndexes.size() > 0) {
                decorValue = new DecorValue(values, attributeIndexes);
                apply((T) view, decorValue);
            }
        } finally {
            values.recycle();
        }
    }

    TypedArray obtainAttributes(Context context, AttributeSet attributeSet) {
        //return context.getResources().obtainAttributes(attributeSet, attrs());
        return context.getTheme().obtainStyledAttributes(attributeSet, styleable(), 0, 0);
    }

    /*static boolean isTypedArrayEmpty(TypedArray typedArray) {
        for (int i = 0; i < typedArray.length(); i++) {
            if(typedArray.getInt(i, -1) != 0) {
                return false;
            }
        }
        return true;
    }*/

    protected abstract int[] styleable();

    /**
     * Attributes supported by this decorator.
     *
     * @return a non-null array of android attr resource ids.
     */
    protected abstract int[] attrs();

    /**
     * The class for the given viewtype. Please be kind and just return the right class here :)
     *
     * @return The class/typetoken for T
     */
    protected abstract Class<T> clazz();

    /**
     * This method will be called if a View of type T was inflated and it had one of the attributes
     * specified by {@link AttrsDecorator#attrs()} set.
     * @param view  The view object that is being decorated.
     * @param decorValue A {@link DecorValue} for each attribute.
     *
     */
    protected abstract void apply(T view, DecorValue decorValue);

}

