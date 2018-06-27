package com.byron.mytalks.PicassoTransformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

public class RadiusTransformation implements Transformation {

    int mRoundPx;
    int mRoundPy;

    public RadiusTransformation(int roundPx, int roundPy) {
        this.mRoundPx = roundPx;
        this.mRoundPy = roundPy;
    }

    /**
     * Transform the source bitmap into a new bitmap. If you create a new bitmap instance, you must
     * call {@link Bitmap#recycle()} on {@code source}. You may return the original
     * if no transformation is required.
     *
     * @param source
     */
    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        canvas.drawARGB(Color.TRANSPARENT,Color.TRANSPARENT, Color.TRANSPARENT,Color.TRANSPARENT);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, mRoundPx, mRoundPy, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect srcRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(source, srcRect, srcRect, paint);

        if(source != null && source != output) {
            source.recycle();
        }

        return output;
    }

    /**
     * Returns a unique key for the transformation, used for caching purposes. If the transformation
     * has parameters (e.g. size, scale factor, etc) then these should be part of the key.
     */
    @Override
    public String key() {
        return "radius";
    }
}
