package mona.android.decor.app;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FragmentsTest {
	private Instrumentation instrumentation;
	private Activity activity;

	@Before
	public void instrument() {
		instrumentation = InstrumentationRegistry.getInstrumentation();
		Intent intent = new Intent(instrumentation.getTargetContext(), CreatesFragActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity = instrumentation.startActivitySync(intent);
	}

	@After
	public void finish() {
		activity.finish();
	}

	@Test
	public void testCreatesFragActivity() throws Exception {
		// test that we have some things in place
		assertThat(activity).isNotNull();
		assertThat(activity.findViewById(R.id.line1)).isNotNull();
	}
}
