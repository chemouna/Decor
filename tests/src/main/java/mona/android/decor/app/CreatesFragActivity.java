package mona.android.decor.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import mona.android.decor.Decor;

public class CreatesFragActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Decor.get(this).withAll(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emty_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.main, new TestFragment()).commit();
		}
	}
}
