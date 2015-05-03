package com.mounacheikhna.decorators;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.View;
import android.view.View.OnLongClickListener;

import com.mounacheikhna.decor.DecorValue;

/**
 * Created by cheikhna on 02/04/2015.
 */
public class OnLongClickDecorator extends OnActionBaseDecorator {

    public OnLongClickDecorator(Activity activity) {
        super(activity);
    }

    @Override
    protected int[] styleable() {
        return R.styleable.OnLongClickDecorator;
    }

    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorOnLongClick};
    }

    @Override
    protected void apply(View view, final TypedArray typedArray) {
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onAction(typedArray.getString(R.styleable.OnLongClickDecorator_decorOnLongClick));
                return true;
            }
        });
    }

}
