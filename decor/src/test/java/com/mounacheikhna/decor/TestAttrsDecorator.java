package com.mounacheikhna.decor;

import android.util.Log;
import android.view.View;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;
import com.mounacheikhna.decor.R;

/**
 * Created by cheikhna on 28/04/2015.
 */
public class TestAttrsDecorator extends AttrsDecorator<View> {

    @Override
    protected int[] attrs() {
        //return new int[] {R.attr.decorAttr1};
        return new int[0];
    }

    @Override
    protected Class<View> clazz() {
        return View.class;
    }

    @Override
    protected void apply(View view, DecorValue decorValue) {
        Log.d("TestAttrsDecorator", "Test");
    }

}
