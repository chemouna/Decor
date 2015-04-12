package mona.android.decor;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;

/**
 * A base class for a decorator that transform certain View subtypes with certain attributes. Useful
 * when you want to extend standard layout inflation to add your own attributes to system widgets.
 * If a view with type {@code View&lt;? extends T&gt;} is inflated and it has one of the attributes
 * returned by the {@link AttrsDecorator#attrs()} method then {@link AttrsDecorator#apply(View, SparseArray)}
 * will be invoked for that view.
 * @param <T> The type or parent type of View that this decorator applies to.
 */
public abstract class AttrsDecorator<T extends View> implements Decorator {

    @Override
    public final void apply(View view, View parent, String name, Context context, AttributeSet attrs) {
        if (!clazz().isAssignableFrom(view.getClass())) {
            return;
        }

        TypedArray values = context.getResources().obtainAttributes(attrs, attrs());
        if (values == null) {
            return;
        }

        SparseArray<TypedValueInfo> typedValues = new SparseArray<>();
        try {
            for (int i = 0; i < values.length(); i++) {
                TypedValue buf = new TypedValue();
                if (values.hasValue(i) && values.getValue(i, buf)) {
                    typedValues.put(attrs()[i], new TypedValueInfo(buf, values, i));
                }
            }
            if(typedValues.size() > 0) {
                // inspection disabled as we do know at this point that view can be cast to T
                //noinspection unchecked
                //TODO : find a better representation to be passed here than duplicating
                // index and parent on each value per attribute
                apply((T) view, typedValues);
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
     * @param values An array of {@link TypedValueInfo} for each attribute.
     *
     */
    protected abstract void apply(T view, SparseArray<TypedValueInfo> values);

    public static class TypedValueInfo {
        private TypedValue typedValue;
        private TypedArray parentArray;
        private int indexInValues;

        public TypedValueInfo(TypedValue typedValue, TypedArray parentArray, int indexInValues) {
            this.typedValue = typedValue;
            this.parentArray = parentArray;
            this.indexInValues = indexInValues;
        }

        public TypedValue getTypedValue() {
            return typedValue;
        }

        public TypedArray getParentArray() {
            return parentArray;
        }

        public int getIndexInValues() {
            return indexInValues;
        }
    }
}