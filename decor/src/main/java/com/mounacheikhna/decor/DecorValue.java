package com.mounacheikhna.decor;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;

/**
 * Created by cheikhna on 21/04/2015.
 */

/**
 * holds information passed to a decorator's apply method
 * TODO: add more doc
 */
public class DecorValue {
    private TypedArray mParentArray;
    private SparseIntArray mAttributeIndexes;

    DecorValue(TypedArray parentArray, SparseIntArray attributeIndexes) {
        this.mParentArray = parentArray;
        this.mAttributeIndexes = attributeIndexes;
    }

    //maybe just delegate or extends from android's TypedArray or TypedValue
    //that already do this and have these methods defined

    public Drawable getDrawable(int attr) {
        int index = mAttributeIndexes.get(attr);
        if (index <= 0) return null; //maybe getDrawable of TypedValue already takes care of this ?
        return mParentArray.getDrawable(mAttributeIndexes.valueAt(attr));
    }

    public String getString(int attr) {
        int index = mAttributeIndexes.get(attr);
        if(index <= 0) return null; //TODO : maybe throw an exception ?
        return mParentArray.getString(index);
    }

    public int getColor(int attr) {
        int index = mAttributeIndexes.get(attr);
        if (index <= 0) return -1;
        return mParentArray.getColor(index, -1);
    }

    public boolean getBoolean(int attr) {
        return getBoolean(attr, false);
    }

    public boolean getBoolean(int attr, boolean defaultValue) {
        int index = mAttributeIndexes.get(attr);
        if(index <= 0) return defaultValue;
        return mParentArray.getBoolean(index, defaultValue);
    }

    public int getDimensionPixelSize(int attr, int defaultValue) {
        int index = mAttributeIndexes.get(attr);
        if(index <= 0) return defaultValue;
        return mParentArray.getDimensionPixelSize(index, defaultValue);
    }

    public float getFloat(int attr, float defaultValue) {
        //TODO: refactor these two lines in a single method
        int index = mAttributeIndexes.get(attr);
        if(index <= 0) return defaultValue;
        return mParentArray.getFloat(index, defaultValue);
    }

}