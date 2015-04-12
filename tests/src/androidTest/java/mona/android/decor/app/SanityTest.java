package mona.android.decor.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.test.UiThreadTest;
import android.test.mock.MockApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SanityTest {
    private Context context;
    private Instrumentation instrumentation;

    @Rule
    public UiThreadRule onUiThread = new UiThreadRule(InstrumentationRegistry.getInstrumentation());

    @Before
    public void instrument() {
        context = InstrumentationRegistry.getContext();
        instrumentation = InstrumentationRegistry.getInstrumentation();
    }

    @Test
    public void testSanity() throws Exception {
        assertThat(true).isTrue();
        assertThat(context).isNotNull();
        assertThat(context.getPackageName()).isEqualTo("mona.android.decor.app.test");
    }

    @Test
    @UiThreadTest
    public void testWrapActivity() throws Exception {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("mona.android.decor.app", "TestActivity"));

        Activity act = instrumentation.newActivity(
                Activity.class,
                context,
                null,
                new MockApplication(),
                intent,
                new ActivityInfo(),
                "",
                null,
                null,
                null);

        //TODO: attachbaseContext
    }
}
