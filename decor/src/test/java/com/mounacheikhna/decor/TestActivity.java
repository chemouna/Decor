package com.mounacheikhna.decor;

import android.app.Activity;
import android.content.Context;

/**
 * Created by cheikhna on 03/05/2015.
 */
public class TestActivity extends Activity {

    private TestAttrsDecorator testAttrsDecorator;

    @Override
    protected void attachBaseContext(Context newBase) {
        testAttrsDecorator = new TestAttrsDecorator();
        super.attachBaseContext(DecorContextWrapper.wrap(newBase)
                .with(testAttrsDecorator));
    }

    public TestAttrsDecorator getTestAttrsDecorator() {
        return testAttrsDecorator;
    }
}
