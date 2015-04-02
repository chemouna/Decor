package mona.android.decor.decorators;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnLongClickListener;

import org.jetbrains.annotations.NotNull;

import mona.android.decor.R;

/**
 * Created by cheikhna on 02/04/2015.
 */
public class OnLongClickDecorator extends OnActionBaseDecorator {

    public OnLongClickDecorator(Activity activity) {
        super(activity);
    }

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[]{R.attr.onLongClick};
    }

    @Override
    protected void apply(View view, int attr, final TypedValue value) {
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onAction(value);
                return true;
            }
        });
    }

}
