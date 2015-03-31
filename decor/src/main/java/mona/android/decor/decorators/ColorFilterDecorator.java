package mona.android.decor.decorators;

import android.util.TypedValue;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

import mona.android.decor.AttrsDecorator;
import mona.android.decor.R;

/**
 * Created by cheikhna on 01/03/2015.
 */
public class ColorFilterDecorator extends AttrsDecorator<ImageView> {

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[]{R.attr.colorFilter};
    }

    @NotNull
    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, int attr, TypedValue value) {
        view.setColorFilter(value.data);
    }

}
