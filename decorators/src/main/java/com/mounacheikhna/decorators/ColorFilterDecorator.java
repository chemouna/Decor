package com.mounacheikhna.decorators;

import android.widget.ImageView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;


/**
 * Created by cheikhna on 01/03/2015.
 */
public class ColorFilterDecorator extends AttrsDecorator<ImageView> {

    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorColorFilter};
    }


    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, DecorValue decorValue) {
        int color = decorValue.getColor(R.attr.decorColorFilter);
        if (color != 0) {
            view.setColorFilter(color);
        }
    }

}
