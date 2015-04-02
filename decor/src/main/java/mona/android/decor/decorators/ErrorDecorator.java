package mona.android.decor.decorators;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.EditText;

import mona.android.decor.AttrsDecorator;
import mona.android.decor.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by cheikhna on 09/02/2015.
 */
public class ErrorDecorator extends AttrsDecorator<EditText> {
    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] { R.attr.error_icon, R.attr.error_text};
    }

    @NotNull
    @Override
    protected Class<EditText> clazz() {
        return EditText.class;
    }

/*    //it seems that AttrsDecorator calls this method two times for each TypedValue and each attr (but what i need is to get them both at the same time)
    @Override
    protected void apply(EditText view, int attr, TypedValue value) { //or maybe apply shouldn't have
                // in its signature here TypedValue but TypedArray since it may have more than one element
                //or have only a single attr
        //TODO: but define in what condition to decorate with an error
        ///if(! view.getText().equals("AB")) {

        //int[] errorIconAttr = new int[] { R.attr.error_icon };
        //int indexOfAttrErrorIcon = 0;

        //TypedArray a = view.getContext().obtainStyledAttributes(value.data, errorIconAttr);
        //Drawable icon = a.getDrawable(indexOfAttrErrorIcon); //the way we are getting the icon here isn't working at all

        if(value.resourceId > 0) {
            Drawable icon = view.getContext().getResources()
                                    .getDrawable(value.resourceId);
            //view.setError(value.string.toString(), icon);
            view.setError(null, icon);
        }

        view.setBackgroundColor(Color.RED);
    }*/

    @Override
    protected void apply(EditText view, @NotNull List<AttrsDecorator<EditText>.DecorParam> decorParams) {
        Drawable errorIcon = null;
        CharSequence errorText = null;
        android.os.Debug.waitForDebugger();
        for (DecorParam decorParam : decorParams) {
            if(decorParam.getAttr() == R.attr.error_icon) {
                errorIcon = view.getContext().getResources().getDrawable(decorParam.getValue().resourceId);
            }
            else if(decorParam.getAttr() ==  R.attr.error_text) {
                errorText = decorParam.getValue().string;
            }
        }
        view.setError(errorText, errorIcon);
    }

}

/*
what can help me here ?
 - test with another non assignable from xml attribute
*/