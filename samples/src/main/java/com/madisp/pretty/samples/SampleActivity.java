package com.madisp.pretty.samples;

import android.app.Activity;
import android.os.Bundle;

import com.madisp.pretty.Pretty;

public class SampleActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Pretty.wrap(this).apply(new FontDecor());
		setContentView(R.layout.main);
	}
}
