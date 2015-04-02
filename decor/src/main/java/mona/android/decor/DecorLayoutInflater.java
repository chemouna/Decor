package mona.android.decor;

import com.android.internal.policy.impl.PhoneLayoutInflater;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by mounacheikhna on 27/03/2015.
 */
public class DecorLayoutInflater extends PhoneLayoutInflater {

    private LayoutInflater.Factory2 wrappedFactory;
    private Decor decor;

    public DecorLayoutInflater(Decor pretty, final Activity activity) {
        super(activity);

        this.decor = pretty;

        // if the activity is a FragmentActivity from the support lib then lets wrap it
        // so the <fragment> tags still work
        try {
            Class<?> fragAct = Class.forName("android.support.v4.app.FragmentActivity");
            if (fragAct != null && fragAct.isInstance(activity)) {
                // FragmentActivity is a Factory1, not Factory2
                wrappedFactory = new LayoutInflater.Factory2() {
                    @Override
                    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                        return onCreateView(name, context, attrs);
                    }

                    @Override
                    public View onCreateView(String name, Context context, AttributeSet attrs) {
                        return activity.onCreateView(name, context, attrs);
                    }
                };
            }
        } catch (Exception ignored) { /* ignored */ }
        super.setFactory2(new DecorLayoutFactory(this, wrappedFactory, pretty));
    }

    protected PrettyLayoutInflater(LayoutInflater original, Context newContext, LayoutInflater.Factory2 wrappedFactory, Decor decor) {
        super(original, newContext);
        this.decor = this.decor;
        super.setFactory2(new DecorLayoutFactory(this, wrappedFactory, this.decor));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new DecorLayoutInflater(this, newContext, wrappedFactory, decor);
    }

    @Override
    public void setFactory(LayoutInflater.Factory factory) {
        // outright evil, warn here?
    }

    @Override
    public void setFactory2(LayoutInflater.Factory2 factory) {
        // outright evil, warn here?
    }
}
