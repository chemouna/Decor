package com.mounacheikhna.decorators;

import android.content.res.TypedArray;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;

/**
 * Created by cheikhna on 15/04/2015.
 */
public class ShimmerDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] styleable() {
        return R.styleable.SearchAnimateDecorator;
    }

    @Override
    protected int[] attrs() {
        return new int[] {R.attr.decorShimmer};
    }

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, TypedArray typedArray) {
        //TODO: implement
    }
}
