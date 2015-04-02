package mona.android.decor.decorators;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import mona.android.decor.AttrsDecorator;

import mona.android.decor.R;

import org.jetbrains.annotations.NotNull;

public class FontDecorator { /* extends AttrsDecorator<TextView> {
    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] { R.attr.typeface_asset };
    }

    @NotNull
    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, int attr, TypedValue value) {
        view.setTypeface(Typeface.createFromAsset(view.getResources().getAssets(), value.string.toString()));
    }
  */
}
