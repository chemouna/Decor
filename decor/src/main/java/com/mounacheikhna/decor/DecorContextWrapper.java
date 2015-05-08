package com.mounacheikhna.decor;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by cheikhna on 05/04/2015.
 */
public class DecorContextWrapper extends ContextWrapper  {

    private DecorLayoutInflater mInflater;
    private List<Decorator> mDecorators;

    /**
     * @param base ContextBase to Wrap.
     */
    public DecorContextWrapper(Context base) {
        super(base);
        mDecorators = new ArrayList<>();
    }

    /**
     * wrap the context
     * @param base ContextBase to Wrap.
     * @return ContextWrapper to pass back to the activity.
     */
    public static DecorContextWrapper wrap(Context base) {
        return new DecorContextWrapper(base);
    }

    public ContextWrapper with(Decorator... decorators) {
        Collections.addAll(mDecorators, decorators);
        return this;
    }

    @Override
    public Object getSystemService(String name) {
        if(LAYOUT_INFLATER_SERVICE.equals(name)) {
            if(mInflater == null) {
                mInflater = new DecorLayoutInflater(LayoutInflater.from(getBaseContext()), this, mDecorators, false);
            }
            return mInflater;
        }
        return super.getSystemService(name);
    }

}
