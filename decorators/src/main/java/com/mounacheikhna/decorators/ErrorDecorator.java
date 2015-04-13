package com.mounacheikhna.decorators;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.widget.EditText;

import com.mounacheikhna.decor.AttrsDecorator;

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
    protected void apply(EditText view,  SparseArray<TypedValueInfo> values) {
        Drawable errorIcon = null;
        CharSequence errorText = null;
        TypedValueInfo errIconValueInfo = values.get(R.attr.decorErrorIcon);
        if(errIconValueInfo != null) {
            errorIcon = errIconValueInfo.getParentArray().getDrawable(errIconValueInfo.getIndexInValues());
        }

        TypedValueInfo errTextValueInfo = values.get(R.attr.decorErrorText);
        if(errTextValueInfo != null) {
            errorText = errTextValueInfo.getParentArray().getString(errTextValueInfo.getIndexInValues());
        }
        view.setError(errorText, errorIcon);
    }

}