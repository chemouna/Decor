package com.madisp.pretty.samples;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import com.madisp.pretty.AttrsDecor;

import org.jetbrains.annotations.NotNull;

public class FontDecor extends AttrsDecor<TextView> {
	@NotNull
	@Override
	protected int[] attrs() {
		return new int[] { R.attr.typeface_asset };
	}

	@NotNull
	@Override
	protected Class<TextView> clazz() {
		return TextView.class;
	}

	@Override
	protected void apply(TextView view, int attr, TypedValue value) {
		view.setTypeface(Typeface.createFromAsset(view.getResources().getAssets(), value.string.toString()));
	}
}
