package com.mounacheikhna.decor;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by cheikhna on 28/04/2015.
 */
public class TestAttrsDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] styleable() {
        return R.styleable.DecorExample;
    }

    @Override
    protected int[] attrs() {
        return new int[] {R.attr.decorAttr};
    }

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView textView, DecorValue decorValue) {
        //Log.d("TestAttrsDecorator", "Test");
        //textView.getContext().obtainStyledAttributes(
                //R.styleable.DecorExample_decorAttr, R.attr.decorAttr;
                //R.styleable.DecorExample, R.attr.decorAttr;
        String decorStrValue = decorValue.parentArray.getString(R.styleable.DecorExample_decorAttr);

    }

}
