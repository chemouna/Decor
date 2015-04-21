package com.mounacheikhna.decorators;

import android.graphics.drawable.Drawable;
import android.widget.EditText;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;

/**
 * Created by cheikhna on 09/02/2015.
 */
public class ErrorDecorator extends AttrsDecorator<EditText> {

    @Override
    protected int[] attrs() {
        return new int[] { R.attr.decorErrorIcon, R.attr.decorErrorText};
    }


    @Override
    protected Class<EditText> clazz() {
        return EditText.class;
    }

    @Override
    protected void apply(EditText view,  DecorValue decorValue) {
        Drawable errorIcon = decorValue.getDrawable(R.attr.decorErrorIcon);
        CharSequence errorText = decorValue.getString(R.attr.decorErrorText);
        view.setError(errorText, errorIcon);
    }

}