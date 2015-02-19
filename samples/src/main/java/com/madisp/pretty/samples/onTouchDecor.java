package com.madisp.pretty.samples;

import android.app.Activity;
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

    private Activity mContainerActivity;

    public onTouchDecor(Activity activity) {
        mContainerActivity = activity;
    }

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
        Log.i("TEST", " apply called ");
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("TEST", " onTouch called with value.string =" + value.string);
                Method mHandler = null;
                try {
                    if(mContainerActivity != null) {
                        //we need to search for this method in activity not in view itself
                        /*mHandler = v.getContext().getClass().getMethod(value.string.toString(),
                                mContainerActivity.getClass());*/
                        Log.i("TEST", " -> trying to get handler for "+ value.string);
                        mHandler = mContainerActivity.getClass().getMethod(value.string.toString());
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                if (mHandler == null) return false;
                try {
                    //mHandler.invoke(v.getContext(), view.getClass());//not sure that view.getClass() does correctly replace View.this
                    Log.i("TEST", " -> invoking "+ value.string + " method");
                    mHandler.invoke(mContainerActivity);
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
