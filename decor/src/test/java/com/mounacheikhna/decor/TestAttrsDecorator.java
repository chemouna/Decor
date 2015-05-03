package com.mounacheikhna.decor;

import android.content.res.TypedArray;
import android.widget.TextView;

/**
 * Created by cheikhna on 28/04/2015.
 */
public class TestAttrsDecorator extends AttrsDecorator<TextView> {

    private String decorStrValue;

    @Override
    protected int[] styleable() {
        return R.styleable.DecorExample;
    }

/*    @Override
    protected int[] attrs() {
        return new int[] {R.attr.decorAttr};
    }*/

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView textView, TypedArray typedArray) {
        decorStrValue = typedArray.getString(R.styleable.DecorExample_decorAttr);
    }

    public String getDecorStrValue() {
        return decorStrValue;
    }
}
