package com.mounacheikhna.decorators;

import android.graphics.Typeface;
import android.util.SparseArray;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;


public class FontDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] attrs() {
        return new int[] { R.attr.decorTypefaceAsset};
    }


    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, SparseArray<TypedValueInfo> values) {
        view.setTypeface(Typeface.createFromAsset(view.getResources().getAssets(),
                values.get(R.attr.decorTypefaceAsset).getTypedValue().string.toString()));
    }

}
