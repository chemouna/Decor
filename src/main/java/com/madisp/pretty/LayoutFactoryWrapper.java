package com.madisp.pretty;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A small helper class to wrap a LayoutInflater's factory.
 */
public abstract class LayoutFactoryWrapper implements LayoutInflater.Factory2 {
	@NotNull
	private final LayoutInflater inflater;
	@Nullable
	private final LayoutInflater.Factory2 base;

	// from com.android.internal.policy.impl.PhoneLayoutInflater
	private static final String[] CLASS_PREFIX_LIST = {
			"android.widget.",
			"android.webkit."
	};

	public LayoutFactoryWrapper(@NotNull LayoutInflater inflater, @Nullable LayoutInflater.Factory2 baseFactory) {
		this.inflater = inflater;
		this.base = baseFactory;
	}

	@Override
	@Nullable
	public View onCreateView(@Nullable View parent, @NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs) {
		View v = null;
		if (base != null) {
			v = base.onCreateView(parent, name, context, attrs);
		}
		if (v == null) {
			// construct a view the same way that android does it
			for (String prefix : CLASS_PREFIX_LIST) {
				try {
					inflater.createView(name, prefix, attrs);
				} catch (ClassNotFoundException ignored) {
					// let v be null, this means that we don't know how to inflate this view
				}
			}
		}
		v = onViewCreated(v, parent, name, context, attrs);
		return v;
	}

	@Override
	@Nullable
	public View onCreateView(@NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs) {
		return onCreateView(null, name, context, attrs);
	}

	@Nullable
	protected abstract View onViewCreated(@Nullable View view, @Nullable View parent, @NotNull String name, @NotNull Context context, @NotNull AttributeSet attrs);
}
