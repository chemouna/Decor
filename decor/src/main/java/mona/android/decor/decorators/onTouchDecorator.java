package mona.android.decor.decorators;

import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import hugo.weaving.DebugLog;
import mona.android.decor.AttrsDecorator;
import mona.android.decor.R;

/**
 * Created by cheikhna on 17/02/2015.
 */
public class OnTouchDecorator { /*extends OnActionBaseDecorator {

    public OnTouchDecorator(Activity activity) {
        super(activity);
    }

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[]{R.attr.onTouch};
    }

    @DebugLog
    @Override
    protected void apply(final View view, int attr, final TypedValue value) {
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return OnTouchDecorator.this.onAction(value);
            }
        });
    }*/

}
