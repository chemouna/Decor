package mona.android.decor;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import hugo.weaving.DebugLog;
import mona.android.decor.decorators.ErrorDecorator;

/**
 * TODO: write a general overview of pretty.
 */
public class Decor {

    private final Collection<Decor> decors = new ArrayList<Decor>();

    private Decor(@NotNull final Activity activity) {
        // ooh, ugly
        try {
            Field f = PhoneWindow.class.getDeclaredField("mLayoutInflater");
            f.setAccessible(true);
            f.set(activity.getWindow(), new DecorLayoutInflater(this, activity));
        } catch (Exception e) {
            throw new IllegalStateException("Failed invoking Pretty.wrap on an Activity.", e);
        }
    }

    /**
     * "Infect" a LayoutInflater in an Activity with a new Pretty instance.
     * @param activity activity whose LayoutInflater to mangle
     * @return An instance of Pretty, see {@link com.madisp.pretty.Pretty#with(Decor)}
     */
    @NotNull
    public static Decor wrap(@NotNull Activity activity) {
        // hide the constructor behind a more stable public "API"
        return new Decor(activity);
    }

    /**
     * Add a decorator to the filter chain.
     * @param decor The decorator to add
     * @return Pretty instance used, allows one to chain multiple with calls.
     */
    @NotNull
    public Decor with(@NotNull Decor decor) {
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

    /**
     * Add all decorators
     */
  /*  @DebugLog
    @NotNull
    public Decor withAll(Activity activity) {
       *//*  with(new CircularImageDecorator())
        .with(new ColorFilterDecorator())*//*
        with(new ErrorDecorator());
        *//*.with(new FontDecorator())
        .with(new RoundDecorator())
        .with(new OnTouchDecorator(activity))
        .with(new OnLongClickDecorator(activity));
        if(VersionUtils.isAtLeastL()) {
            with(new SearchAnimateDecorator());
        }*//*
        return this;
    }*/

}
