package com.mounacheikhna.decorators;

import android.util.SparseArray;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;

/**
 * Created by cheikhna on 15/04/2015.
 */
public class ShimmerDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] attrs() {
        return new int[] {R.attr.decorShimmer};
    }

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, SparseArray<TypedValueInfo> values) {


    }
}
