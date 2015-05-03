package com.mounacheikhna.decorators;

/**
 * Created by cheikhna on 09/04/2015.
 */

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.mounacheikhna.decor.AttrsDecorator;

/**
 * A decorator for a TextView to resize it's text to be no larger than the width of the view.
 * (An example of a complicated decorator )
 */
public class AutofitDecorator extends AttrsDecorator<TextView> {

    private static final String TAG = "AutofitDecorator";
    private static final boolean SPEW = false;

    private TextView mTextView;

    // Minimum size of the text in pixels
    private static final int DEFAULT_MIN_TEXT_SIZE = 11; //sp

    // How precise we want to be when reaching the target textWidth size
    private static final float PRECISION = 0.5f;

    // Attributes
    private boolean mSizeToFit;
    private int mMaxLines;
    private float mMinTextSize;
    private float mMaxTextSize;

    private float mPrecision;

    private TextPaint mPaint;


    @Override
    protected int[] styleable() {
        return R.styleable.AutofitDecorator;
    }

    @Override
    protected Class<TextView> clazz() {
        return TextView.class;
    }

    @Override
    protected void apply(TextView view, TypedArray typedArray) {
        mTextView = view;
        boolean isAutofit = typedArray.getBoolean(R.styleable.AutofitDecorator_decorAutofitText, false);
        if(!isAutofit) return;

        float scaledDensity = mTextView.getContext().getResources().getDisplayMetrics().scaledDensity;
        boolean sizeToFit = true;
        int minTextSize = (int) scaledDensity * DEFAULT_MIN_TEXT_SIZE;
        float precision = PRECISION;

        //TODO: deal with case when one of these values is absent

        sizeToFit = typedArray.getBoolean(R.styleable.AutofitDecorator_decorSizeToFit, sizeToFit);

        minTextSize = typedArray.getDimensionPixelSize(R.styleable.AutofitDecorator_decorMinTextSize, minTextSize);

        precision = typedArray.getFloat(R.styleable.AutofitDecorator_decorPrecision, precision);

        mPaint = new TextPaint();
        setSizeToFit(sizeToFit);
        setRawTextSize(mTextView.getTextSize());
        setRawMinTextSize(minTextSize);
        setPrecision(precision);
    }

    /**
     * If true, the text will automatically be resized to fit its constraints; if false, it will act
     * like a normal TextView.
     */
    public void setSizeToFit(boolean sizeToFit) {
        mSizeToFit = sizeToFit;
        refitText();
    }

    private void setRawTextSize(float size) {
        if (size != mMaxTextSize) {
            mMaxTextSize = size;
            refitText();
        }
    }

    private void setRawMinTextSize(float minSize) {
        if (minSize != mMinTextSize) {
            mMinTextSize = minSize;
            refitText();
        }
    }

    /**
     * Set the amount of precision used to calculate the correct text size to fit within it's
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    public void setPrecision(float precision) {
        if (precision != mPrecision) {
            mPrecision = precision;
            refitText();
        }
    }

    /**
     * @return the amount of precision used to calculate the correct text size to fit within it's
     * bounds.
     */
    public float getPrecision() {
        return mPrecision;
    }


    /**
     * Re size the font so the specified text fits in the text box assuming the text box is the
     * specified width.
     */
    private void refitText() {
        if (!mSizeToFit) {
            return;
        }

        if (mMaxLines <= 0) {
            // Don't auto-size since there's no limit on lines.
            return;
        }

        String text = mTextView.getText().toString();
        int targetWidth = mTextView.getWidth() - mTextView.getPaddingLeft() - mTextView.getPaddingRight();
        if (targetWidth > 0) {
            Context context = mTextView.getContext();
            Resources r = Resources.getSystem();
            DisplayMetrics displayMetrics;

            float size = mMaxTextSize;
            float high = size;
            float low = 0;

            if (context != null) {
                r = context.getResources();
            }
            displayMetrics = r.getDisplayMetrics();

            mPaint.set(mTextView.getPaint());
            mPaint.setTextSize(size);

            if ((mMaxLines == 1 && mPaint.measureText(text) > targetWidth)
                    || getLineCount(text, mPaint, size, targetWidth, displayMetrics) > mMaxLines) {
                size = getTextSize(text, mPaint, targetWidth, mMaxLines, low, high, mPrecision,
                        displayMetrics);
            }

            if (size < mMinTextSize) {
                size = mMinTextSize;
            }

            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    /**
     * Recursive binary search to find the best size for the text
     */
    private static float getTextSize(String text, TextPaint paint,
                                     float targetWidth, int maxLines,
                                     float low, float high, float precision,
                                     DisplayMetrics displayMetrics) {
        float mid = (low + high) / 2.0f;
        int lineCount = 1;
        StaticLayout layout = null;

        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid,
                displayMetrics));

        if (maxLines != 1) {
            layout = new StaticLayout(text, paint, (int) targetWidth, Layout.Alignment.ALIGN_NORMAL,
                    1.0f, 0.0f, true);
            lineCount = layout.getLineCount();
        }

        if (SPEW) {
            Log.d(TAG, "low=" + low + " high=" + high + " mid=" + mid +
                    " target=" + targetWidth + " maxLines=" + maxLines + " lineCount=" + lineCount);
        }

        if (lineCount > maxLines) {
            return getTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                    displayMetrics);
        } else if (lineCount < maxLines) {
            return getTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                    displayMetrics);
        } else {
            float maxLineWidth = 0;
            if (maxLines == 1) {
                maxLineWidth = paint.measureText(text);
            } else {
                for (int i = 0; i < lineCount; i++) {
                    if (layout.getLineWidth(i) > maxLineWidth) {
                        maxLineWidth = layout.getLineWidth(i);
                    }
                }
            }

            if ((high - low) < precision) {
                return low;
            } else if (maxLineWidth > targetWidth) {
                return getTextSize(text, paint, targetWidth, maxLines, low, mid, precision,
                        displayMetrics);
            } else if (maxLineWidth < targetWidth) {
                return getTextSize(text, paint, targetWidth, maxLines, mid, high, precision,
                        displayMetrics);
            } else {
                return mid;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    //@Override //since we cant have override with it here -> issue when frmwrk calls setTextSize on this afterwards
                //probably it wont work (is there a way to have a delegate for mTextView to pass to this when setTextSize is called on it)
    public void setTextSize(int unit, float size) {
        if (!mSizeToFit) {
            mTextView.setTextSize(unit, size);
        }

        Context context = mTextView.getContext();
        Resources r = Resources.getSystem();

        if (context != null) {
            r = context.getResources();
        }

        setRawTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()));
    }

    private static int getLineCount(String text, TextPaint paint, float size, float width,
                                    DisplayMetrics displayMetrics) {
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size,
                displayMetrics));
        StaticLayout layout = new StaticLayout(text, paint, (int) width,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        return layout.getLineCount();
    }

}
