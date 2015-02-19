package com.madisp.pretty.samples;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.TypedValue;
import android.widget.ImageView;

import com.madisp.pretty.AttrsDecor;

import org.jetbrains.annotations.NotNull;

/**
 * Created by cheikhna on 17/02/2015.
 */
public class RoundDecor extends AttrsDecor<ImageView> {

    @NotNull
    @Override
    protected int[] attrs() {
        return new int[] {R.attr.round, R.attr.roundDrawable};
    }

    @NotNull
    @Override
    protected Class<ImageView> clazz() {
        return ImageView.class;
    }

    @Override
    protected void apply(ImageView view, int attr, TypedValue value) {
        Bitmap bitmap = BitmapFactory.decodeResource(view.getContext().getResources(), value.resourceId);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        RectF rect = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());

        // rect contains the bounds of the shape
        // radius is the radius in pixels of the rounded corners
        // paint contains the shader that will texture the shape

        //canvas.drawRoundRect(rect, radius, radius, paint); //-> somehow we need access to canvas here ?
    }

}