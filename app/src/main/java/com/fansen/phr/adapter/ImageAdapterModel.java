package com.fansen.phr.adapter;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/11/10.
 */
public class ImageAdapterModel implements Serializable{
    private String imagePath;
    private Bitmap thumbnailBitmap;

    public ImageAdapterModel() {
    }

    public ImageAdapterModel(String imagePath, Bitmap bitmap) {
        this.imagePath = imagePath;
        this.thumbnailBitmap = bitmap;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getThumbnailBitmap() {
        return thumbnailBitmap;
    }

    public void setThumbnailBitmap(Bitmap thumbnailBitmap) {
        this.thumbnailBitmap = thumbnailBitmap;
    }
}
