package com.mounacheikhna.decorators;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.mounacheikhna.decor.AttrsDecorator;

/**
 * Created by cheikhna on 09/02/2015.
 */
public class ErrorDecorator extends AttrsDecorator<EditText> {

    @Override
    protected int[] styleable() {
        return R.styleable.ErrorDecorator;
    }

    @Override
    protected Class<EditText> clazz() {
        return EditText.class;
    }

    @Override
    protected void apply(EditText view, TypedArray typedArray) {
        Drawable errorIcon = typedArray.getDrawable(R.styleable.ErrorDecorator_decorErrorIcon);
        CharSequence errorText = typedArray.getString(R.styleable.ErrorDecorator_decorErrorText);
        view.setError(errorText, errorIcon);
    }

}