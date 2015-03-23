package mona.android.decor.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import mona.android.decor.Decor;
import mona.android.decor.decorators.CircularImageDecorator;
import mona.android.decor.decorators.ErrorDecorator;
import mona.android.decor.decorators.FontDecorator;
import mona.android.decor.decorators.RoundDecorator;
import mona.android.decor.decorators.onTouchDecorator;

public class SampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Maybe have in the lib something that by default adds all of those base decors
        //to not require a user of this to have to all of this !!
        Decor decor = Decor.with(this).with(new FontDecorator()).with(new ErrorDecorator())
                                         .with(new RoundDecorator()).with(new CircularImageDecorator());
        decor.with(new onTouchDecorator(this));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
       Log.i("TEST", " onIvTouch (from activity) called on touch ");
    }

}