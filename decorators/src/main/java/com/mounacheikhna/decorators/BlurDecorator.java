package com.mounacheikhna.decorators;

import android.content.res.TypedArray;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decorators.utils.BlurUtils;

/**
 * Created by cheikhna on 03/04/2015.
 */
public class BlurDecorator extends AttrsDecorator<View> {

    @Override
    protected int[] styleable() {
        return R.styleable.BlurDecorator;
    }

    @Override
    protected Class<View> clazz() {
        return View.class;
    }

    @Override
    protected void apply(View view, TypedArray typedArray) {
        boolean isBlur = typedArray.getBoolean(R.styleable.BlurDecorator_decorBlur, false);
        if(!isBlur || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) return;

        //i'm guessing this part here doesnt work because i'm using the same blur as ngAndroid and they are
        //doing their injection in a different point in the inflation
        ViewGroup parent = (ViewGroup) view.getParent();
        FrameLayout layout = new FrameLayout(view.getContext());
        layout.setLayoutParams(view.getLayoutParams());
        int index = parent.indexOfChild(view);
        parent.removeViewAt(index);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        layout.addView(view);
        parent.addView(layout, index);

        final ImageView imageView = new ImageView(view.getContext());
        imageView.setLayoutParams(layoutParams);
        imageView.setVisibility(View.GONE);
        layout.addView(imageView);

        view.setVisibility(View.GONE);
        view.setDrawingCacheEnabled(true);
        imageView.setImageBitmap(BlurUtils.blurBitmap(view.getDrawingCache(), view.getContext()));
        imageView.setVisibility(View.VISIBLE);
    }

}