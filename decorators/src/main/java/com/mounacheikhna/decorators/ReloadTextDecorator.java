package com.mounacheikhna.decorators;

import android.content.res.TypedArray;
import android.widget.TextView;
import com.mounacheikhna.decor.AttrsDecorator;

/**
 * Created by m.cheikhna on 11/08/2016.
 */
public class ReloadTextDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] styleable() {
        return R.styleable.ReloadTextDecorator;
    }

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, TypedArray typedArray) {
        //TODO: the problem is to find a redo-able thing here
        //maybe having just a callback -> then you cant trigger it
        //i need a way to watch change to something and reload in that case

        //or suppose we have every time our StringsTextView
        //then in BaseActivity reload that goes through all strings and refreshes

    }
}
