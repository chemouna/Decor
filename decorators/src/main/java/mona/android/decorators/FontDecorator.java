package mona.android.decorators;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.widget.TextView;

import mona.android.decor.AttrsDecorator;


public class FontDecorator extends AttrsDecorator<TextView> {

    @Override
    protected int[] attrs() {
        return new int[] { R.attr.decorTypefaceAsset};
    }


    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, SparseArray<TypedValueInfo> values) {
        view.setTypeface(Typeface.createFromAsset(view.getResources().getAssets(),
                values.get(R.attr.decorTypefaceAsset).getTypedValue().string.toString()));
    }

}
