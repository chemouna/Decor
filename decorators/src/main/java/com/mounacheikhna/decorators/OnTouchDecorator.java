package com.mounacheikhna.decorators;

import android.app.Activity;
import android.content.res.TypedArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by cheikhna on 17/02/2015.
 */
public class OnTouchDecorator extends OnActionBaseDecorator {

    public OnTouchDecorator(Activity activity) {
        super(activity);
    }


    @Override
    protected int[] styleable() {
        return R.styleable.OnTouchDecorator;
    }

    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorOnTouch};
    }

    @Override
    protected void apply(View view, final TypedArray typedArray) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return onAction(typedArray.getString(R.styleable.OnTouchDecorator_decorOnTouch));
            }
        });
    }

}
