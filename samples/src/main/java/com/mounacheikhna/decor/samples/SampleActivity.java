package com.mounacheikhna.decor.samples;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.mounacheikhna.decor.DecorContextWrapper;
import com.mounacheikhna.decorators.Decorators;

public class SampleActivity extends FragmentActivity /*ActionBarActivity*/ /*Activity*/ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
        Toast.makeText(this, " Touch detected ", Toast.LENGTH_SHORT).show();
    }

    public void onRobotLongClick() {
        Toast.makeText(this, " Long click detected ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(DecorContextWrapper.wrap(newBase)
                .with(Decorators.getAll()));
    }

}