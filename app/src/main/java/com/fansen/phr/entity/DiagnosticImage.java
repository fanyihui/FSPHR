package com.fansen.phr.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/11/16.
 */
public class DiagnosticImage implements Serializable{
    private int _id;
    private String captureImageUri;
    private String thumbnailImageUri;
    private String creatingDate;
    private byte[] thumbnailImage;

    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCaptureImageUri() {
        return captureImageUri;
    }

    public void setCaptureImageUri(String captureImageUri) {
        this.captureImageUri = captureImageUri;
    }

    public String getThumbnailImageUri() {
        return thumbnailImageUri;
    }

    public void setThumbnailImageUri(String thumbnailImageUri) {
        this.thumbnailImageUri = thumbnailImageUri;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }
}
