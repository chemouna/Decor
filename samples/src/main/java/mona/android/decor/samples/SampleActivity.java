package mona.android.decor.samples;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import mona.android.decor.DecorContextWrapper;
import mona.android.decor.Decorator;
import mona.android.decorators.Decorators;

public class SampleActivity extends /*ActionBarActivity*/ /*FragmentActivity*/ Activity {

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