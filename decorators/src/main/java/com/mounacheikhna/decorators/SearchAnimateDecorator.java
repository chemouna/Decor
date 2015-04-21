package com.mounacheikhna.decorators;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.mounacheikhna.decor.AttrsDecorator;
import com.mounacheikhna.decor.DecorValue;

/**
 * Created by mounacheikhna on 01/04/2015.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SearchAnimateDecorator extends AttrsDecorator<ImageView> {

    private boolean expanded = false;
    private ImageView mIv;
    private AnimatedVectorDrawable mSearchToBar;
    private AnimatedVectorDrawable mBarToSearch;
    private Interpolator mInterp;
    private int mDuration;
    private float mOffset;


    @Override
    protected int[] attrs() {
        return new int[]{R.attr.decorAnimateSearch};
    }


    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, DecorValue decorValue) {
        boolean shouldAnimate = decorValue.getBoolean(R.attr.decorAnimateSearch);
        if(!shouldAnimate) return;

        mIv = view;
        mSearchToBar = (AnimatedVectorDrawable) view.getContext().getResources().getDrawable(R.drawable.anim_search_to_bar);
        mBarToSearch = (AnimatedVectorDrawable) view.getContext().getResources().getDrawable(R.drawable.anim_bar_to_search);
        mInterp = AnimationUtils.loadInterpolator(view.getContext(), android.R.interpolator.linear_out_slow_in);
        mDuration = view.getContext().getResources().getInteger(R.integer.duration_bar);
        // iv is sized to hold the search+bar so when only showing the search icon, translate the
        // whole view left by half the difference to keep it centered
        mOffset = -71f * (int) view.getContext().getResources().getDisplayMetrics().scaledDensity;
        view.setTranslationX(mOffset);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate(v);
            }
        });
    }

    //TODO: do a version of this for pre-lollipop or dont allow the use of it
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animate(View view) {
        if (!expanded) {
            mIv.setImageDrawable(mSearchToBar);
            mSearchToBar.start();
            mIv.animate().translationX(0f).setDuration(mDuration).setInterpolator(mInterp);
            //text.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);
        } else {
            mIv.setImageDrawable(mBarToSearch);
            mBarToSearch.start();
            mIv.animate().translationX(mOffset).setDuration(mDuration).setInterpolator(mInterp);
            //text.setAlpha(0f);
        }
        expanded = !expanded;
    }

}
