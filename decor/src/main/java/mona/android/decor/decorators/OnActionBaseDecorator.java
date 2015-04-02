package mona.android.decor.decorators;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hugo.weaving.DebugLog;
import mona.android.decor.AttrsDecorator;

/**
 * Created by cheikhna on 02/04/2015.
 */
public abstract class OnActionBaseDecorator extends AttrsDecorator<View> {

    protected Activity mContainerActivity;

    public OnActionBaseDecorator(Activity activity) {
        mContainerActivity = activity;
    }

    @NotNull
    @Override
    protected abstract int[] attrs();

    @NotNull
    @Override
    protected Class<View> clazz() {
        return View.class;
    }

    @Override
    protected abstract void apply(View view, int attr, TypedValue value);

    protected boolean onAction(TypedValue value) {
        Method mHandler = null;
        try {
            if(mContainerActivity != null) {
                mHandler = mContainerActivity.getClass().getMethod(value.string.toString());
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
