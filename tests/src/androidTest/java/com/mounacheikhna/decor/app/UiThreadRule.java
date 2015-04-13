package com.mounacheikhna.decor.app;

import android.app.Instrumentation;
import android.test.UiThreadTest;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * A JUnit4 rule for Android Instrumentation tests
 */
public class UiThreadRule implements TestRule {
	private final Instrumentation instrumentation;

	public UiThreadRule(Instrumentation instrumentation) {
		this.instrumentation = instrumentation;
	}

	@Override
	public Statement apply(final Statement statement, Description description) {
		Collection<Annotation> annotations = description.getAnnotations();

		for (Annotation annotation : annotations) {
			if (UiThreadTest.class.equals(annotation.annotationType())) {
				return new Statement() {
					@Override
					public void evaluate() throws Throwable {
						final Throwable[] t = new Throwable[1];
						instrumentation.runOnMainSync(new Runnable() {
							@Override
							public void run() {
								try {
									statement.evaluate();
								} catch (Throwable throwable) {
									t[0] = throwable;
								}
							}
						});
						if (t[0] != null) {
							throw t[0];
						}
					}
				};
			}
		}
		return statement;
	}
}
