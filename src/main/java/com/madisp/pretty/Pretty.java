package com.madisp.pretty;

import android.app.Activity;
import android.view.LayoutInflater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO: write a general overview of pretty.
 */
public class Pretty {
	private final Collection<Decor> decors = new ArrayList<Decor>();

	private Pretty(@NotNull Activity activity) {
		LayoutInflater inflater = activity.getLayoutInflater();
		LayoutInflater.Factory2 factory = inflater.getFactory2();
		inflater.setFactory2(new PrettyLayoutFactory(inflater, factory, this));
	}

	/**
	 * "Infect" a LayoutInflater in an Activity with a new Pretty instance.
	 * @param activity activity whose LayoutInflater to mangle
	 * @return An instance of Pretty, see {@link com.madisp.pretty.Pretty#apply(Decor)}
	 */
	@NotNull
	public static Pretty wrap(@NotNull Activity activity) {
		// hide the constructor behind a more stable public "API"
		return new Pretty(activity);
	}

	/**
	 * Add a decorator to the filter chain.
	 * @param decor The decorator to add
	 * @return Pretty instance used, allows one to chain multiple apply calls.
	 */
	@NotNull
	public Pretty apply(@NotNull Decor decor) {
		decors.add(decor);
		return this;
	}

	/**
	 * @return The list of decorators registered so far.
	 */
	@NotNull
	public Collection<Decor> getDecors() {
		return decors;
	}
}
