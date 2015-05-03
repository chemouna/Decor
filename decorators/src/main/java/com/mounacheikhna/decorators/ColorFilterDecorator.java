package com.mounacheikhna.decorators;

import android.content.res.TypedArray;
import android.widget.ImageView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;


/**
 * Created by cheikhna on 01/03/2015.
 */
public class ColorFilterDecorator extends AttrsDecorator<ImageView> {

    @Override
    protected int[] styleable() {
        return R.styleable.ColorFilterDecorator;
    }

    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorColorFilter};
    }


    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, TypedArray typedArray) {
        int color = typedArray.getColor(R.styleable.ColorFilterDecorator_decorColorFilter, 0);
        if (color != 0) {
            view.setColorFilter(color);
        }
    }

}
