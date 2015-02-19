package com.madisp.pretty.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.madisp.pretty.Pretty;

public class SampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Pretty pretty = Pretty.wrap(this).with(new FontDecor()).with(new ErrorDecor());
        pretty.with(new onTouchDecor(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
       Log.i("TEST", " onIvTouch (from activity) called on touch ");
    }

}