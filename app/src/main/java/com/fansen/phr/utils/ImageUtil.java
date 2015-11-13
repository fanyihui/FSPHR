package com.fansen.phr.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by 310078142 on 2015/11/12.
 */
public class ImageUtil {
    public static Bitmap getBitmap(Bitmap bitmap, int screenWidth,
                                   int screenHight)
    {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scale = (float) screenWidth / w;
        float scale2 = (float) screenHight / h;

        scale = (scale > scale2) ? scale2 : scale;

        matrix.postScale(scale, scale);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return bmp;
    }
}
