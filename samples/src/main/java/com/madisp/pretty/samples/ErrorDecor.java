package com.madisp.pretty.samples;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import com.madisp.pretty.AttrsDecor;

import org.jetbrains.annotations.NotNull;

/**
 * Created by cheikhna on 09/02/2015.
 */
public class ErrorDecor extends AttrsDecor<TextView> {
    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] { R.attr.error_text, R.attr.error_icon };
    }

    @NotNull
    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, int attr, TypedValue value) {
        //TODO: but define in what condition to decorate with an error
        view.setError(value.string.toString(), view.getContext().getResources().getDrawable(value.resourceId));
    }
}
