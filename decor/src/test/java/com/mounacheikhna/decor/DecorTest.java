package com.mounacheikhna.decor;

/**
 * Created by cheikhna on 03/05/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * An overal test with activities
 */
@RunWith(RobolectricTestRunner.class)
//@Config(manifest = Config.NONE)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class DecorTest {

    private TestActivity activity;
    private View testView;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(TestActivity.class).create().get();
        testView = View.inflate(activity, R.layout.test_layout, null);

    }

    @Test
    public void attrsApplied() throws Exception {
        assertThat(activity.getTestAttrsDecorator().values).isNotNull();
        assertThat(activity.getTestAttrsDecorator().attributeIndexes.size()).isGreaterThan(0);
    }

}
