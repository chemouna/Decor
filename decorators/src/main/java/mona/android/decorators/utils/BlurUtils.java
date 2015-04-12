package mona.android.decorators.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by cheikhna on 04/04/2015.
 */
public class BlurUtils {
    private static final float SCALE_RATIO = 5f;
    private static final float DEFAULT_BLUR_RADIUS = 5.f;

    public static Bitmap blurBitmap(Bitmap bitmap, Context context){
        int width = bitmap.getWidth(), height = bitmap.getHeight();
        Bitmap b = Bitmap.createScaledBitmap(Bitmap.createScaledBitmap(bitmap,(int)(width/SCALE_RATIO), (int)(height/SCALE_RATIO), false), width, height, false);
        return blurBitmap(b, DEFAULT_BLUR_RADIUS, context);
    }


    private static Bitmap blurBitmap(Bitmap src, float blurRadius, Context context) {
        RenderScript rs = RenderScript.create(context);
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap blurredBitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), conf);

        final Allocation input = Allocation.createFromBitmap(rs, src, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());

        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(blurRadius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(blurredBitmap);
        return blurredBitmap;
    }

}
