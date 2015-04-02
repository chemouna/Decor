package mona.android.decor.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import mona.android.decor.Decor;

public class SampleActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Maybe have in the lib something that by default adds all of those base decors
        //to not require a user of this to have to all of this !!
       /* Decor decor = Decor.with(this).with(new FontDecorator()).with(new ErrorDecorator())
                                         .with(new RoundDecorator()).with(new CircularImageDecorator());
        decor.with(new onTouchDecorator(this));*/

        Decor decor = Decor.get(this).withAll(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onIvTouch() {
        Toast.makeText(this, " Touch detected ", Toast.LENGTH_SHORT).show();
    }

    public void onRobotLongClick() {
        Toast.makeText(this, " Long click detected ", Toast.LENGTH_SHORT).show();
    }
}