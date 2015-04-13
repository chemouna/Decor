package com.mounacheikhna.decorators;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnLongClickListener;

/**
 * Created by cheikhna on 02/04/2015.
 */
public class OnLongClickDecorator extends OnActionBaseDecorator {

    public OnLongClickDecorator(Activity activity) {
        super(activity);
    }


    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorOnLongClick};
    }

    @Override
    protected void apply(View view, final SparseArray<TypedValueInfo> values) {
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onAction(values.get(R.attr.decorOnLongClick).getTypedValue());
                return true;
            }
        });
    }

}
