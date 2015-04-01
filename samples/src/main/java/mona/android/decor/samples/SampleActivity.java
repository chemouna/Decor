package mona.android.decor.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import mona.android.decor.Decor;

public class SampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Maybe have in the lib something that by default adds all of those base decors
        //to not require a user of this to have to all of this !!
       /* Decor decor = Decor.get(this).get(new FontDecorator()).get(new ErrorDecorator())
                                         .get(new RoundDecorator()).get(new CircularImageDecorator());
        decor.get(new onTouchDecorator(this));*/

        Decor decor = Decor.get(this).withAll(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
       Log.i("TEST", " onIvTouch (from activity) called on touch ");
    }

}