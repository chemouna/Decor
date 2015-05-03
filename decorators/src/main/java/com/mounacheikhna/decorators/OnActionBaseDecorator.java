package com.mounacheikhna.decorators;

import android.app.Activity;
import android.view.View;

import com.mounacheikhna.decor.AttrsDecorator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by cheikhna on 02/04/2015.
 */
public abstract class OnActionBaseDecorator extends AttrsDecorator<View> {

    protected Activity mContainerActivity;

    public OnActionBaseDecorator(Activity activity) {
        mContainerActivity = activity;
    }

    @Override
    protected Class<View> clazz() {
        return View.class;
    }

    protected boolean onAction(String methodName) {
        Method mHandler = null;
        try {
            if(mContainerActivity != null) {
                mHandler = mContainerActivity.getClass().getMethod(methodName);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (mHandler == null) return false;
        try {
            mHandler.invoke(mContainerActivity);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not execute non public method of the activity", e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not execute method of the activity", e);
        }
    }

}
