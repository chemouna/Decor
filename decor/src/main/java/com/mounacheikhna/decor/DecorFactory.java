package com.mounacheikhna.decor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;

/**
 * Created by cheikhna on 05/04/2015.
 */
public class DecorFactory {

    private final Collection<Decorator> decorators;

    public DecorFactory(Collection<Decorator> decorators) {
        this.decorators = decorators;
    }

    public View onViewCreated(View view,  String name,  View parent,  Context context, AttributeSet attrs) {
        if (view == null) {
            return null;
        }
        for (Decorator d : decorators) {
            d.apply(view, parent, name, context, attrs);
        }
        return view;
    }

    public View onViewCreated(View view, Context context, AttributeSet attrs) {
        //TODO: implement
        return view;
    }

}
