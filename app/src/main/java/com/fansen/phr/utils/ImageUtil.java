package com.fansen.phr.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by 310078142 on 2015/11/12.
 */
public class ImageUtil {
    public static Bitmap getSquareBitmap(String imageFile, int width)
    {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;


		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if (width > 0) {
            scaleFactor = photoW / width;
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile, bmOptions);

        return bitmap;
    }

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
