package com.mounacheikhna.decorators;

import android.util.SparseArray;
import android.widget.ImageView;

import com.mounacheikhna.decor.AttrsDecorator;


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
    protected void apply(ImageView view, SparseArray<TypedValueInfo> values) {
        TypedValueInfo typedValueInfo = values.get(R.attr.decorColorFilter);
        int color = typedValueInfo.getParentArray().getColor(typedValueInfo.getIndexInValues(), -1);
        if(color == -1) return;
        view.setColorFilter(color);
    }

}
