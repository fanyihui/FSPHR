package com.fansen.phr.adapter;

import android.graphics.Bitmap;

/**
 * Created by 310078142 on 2015/11/10.
 */
public class ImageAdapterModel {
    private String imagePath;
    private Bitmap bitmap;

    public ImageAdapterModel() {
    }

    public ImageAdapterModel(String imagePath, Bitmap bitmap) {
        this.imagePath = imagePath;
        this.bitmap = bitmap;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
