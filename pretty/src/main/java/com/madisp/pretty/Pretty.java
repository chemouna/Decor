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
		if (inflater.getFactory2() != null) {
			throw new IllegalStateException(
					"Trying to Pretty.wrap an activity that already has a layoutinflater factory " +
					"set. Try calling Pretty.wrap before super.onCreate, especially if you're " +
					"using SupportFragmentActivity/FragmentActivity.");
		}
		LayoutInflater.Factory2 wrappedFactory = null;
		// if the activity is a FragmentActivity from the support lib then lets wrap it
		// so the <fragment> tags still work
		try {
			Class<?> fragAct = Class.forName("android.support.v4.app.FragmentActivity");
			if (fragAct != null && fragAct.isInstance(activity)) {
				wrappedFactory = activity;
			}
		} catch (Exception _) { /* ignored */ }
		inflater.setFactory2(new PrettyLayoutFactory(inflater, wrappedFactory, this));
	}

	/**
	 * "Infect" a LayoutInflater in an Activity with a new Pretty instance.
	 * @param activity activity whose LayoutInflater to mangle
	 * @return An instance of Pretty, see {@link com.madisp.pretty.Pretty#with(Decor)}
	 */
	@NotNull
	public static Pretty wrap(@NotNull Activity activity) {
		// hide the constructor behind a more stable public "API"
		return new Pretty(activity);
	}

	/**
	 * Add a decorator to the filter chain.
	 * @param decor The decorator to add
	 * @return Pretty instance used, allows one to chain multiple with calls.
	 */
	@NotNull
	public Pretty with(@NotNull Decor decor) {
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
