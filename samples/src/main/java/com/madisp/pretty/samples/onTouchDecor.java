package com.madisp.pretty.samples;

import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.madisp.pretty.AttrsDecor;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by cheikhna on 17/02/2015.
 */
public class onTouchDecor extends AttrsDecor<View> {

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[]{R.attr.onTouch};
    }

    @NotNull
    @Override
    protected Class<View> clazz() {
        return View.class;
    }

    @Override
    protected void apply(final View view, int attr, final TypedValue value) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Method mHandler = null;
                Log.i("TEST", " value.string = " + value.string);
                try {
                    mHandler = v.getContext().getClass().getMethod(value.string.toString(),
                            View.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                if (mHandler == null) return false;
                try {
                    mHandler.invoke(v.getContext(), view.getClass());//not sure that view.getClass() does correctly replace View.this
                    return true;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Could not execute non "
                            + "public method of the activity", e);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Could not execute "
                            + "method of the activity", e);
                }
            }
        });
    }

}
