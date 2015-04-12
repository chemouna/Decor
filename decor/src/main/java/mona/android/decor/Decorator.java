package mona.android.decor;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * A class that operates on already constructed views, i.e., decorates them.
 */
public interface Decorator {
    /**
     * Decorates the given view.
     * This method will be called by Decor for every {@link View} created in the layout
     * @param view The view to decorate. Never null.
     * @param parent The parent view, if available. May be null.
     * @param name The name of the tag in the layout file, e.g. {@code TextView}.
     * @param context The context where the view was constructed in.
     * @param attrs A read-only set of tag attributes.
     */
    public void apply( View view,  View parent,  String name,  Context context,  AttributeSet attrs);

}
