package com.madisp.pretty.samples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.madisp.pretty.Pretty;

public class SampleActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Pretty.wrap(this).apply(new FontDecor());
		setContentView(R.layout.main);
	}
}
