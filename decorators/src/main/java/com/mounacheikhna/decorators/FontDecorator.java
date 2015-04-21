package com.mounacheikhna.decorators;

import android.graphics.Typeface;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;


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
    protected void apply(TextView view, DecorValue decorValue) {
        String typefacePath = decorValue.getString(R.attr.decorTypefaceAsset);
        if(typefacePath == null) return;
        view.setTypeface(Typeface.createFromAsset(
                view.getResources().getAssets(), typefacePath));
    }
}