package com.madisp.pretty.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.madisp.pretty.Pretty;

public class SampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Pretty.wrap(this).with(new FontDecor()).with(new onTouchDecor()).with(new ErrorDecor());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
       Log.i("TEST", " onIvTouch called on touch ");
    }

}