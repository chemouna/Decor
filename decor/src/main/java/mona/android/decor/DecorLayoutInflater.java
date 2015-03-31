package mona.android.decor;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * Created by mounacheikhna on 27/03/2015.
 */
public class DecorLayoutInflater extends LayoutInflater {

    protected DecorLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }


    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new DecorLayoutInflater(this, newContext);
    }
}
