package com.mounacheikhna.decorators;

import android.app.Activity;
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
    protected int[] attrs() {
        return new int[]{R.attr.decorOnLongClick};
    }

    @Override
    protected void apply(View view, final DecorValue decorValue) {
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onAction(decorValue.getString(R.attr.decorOnLongClick));
                return true;
            }
        });
    }

}
