package com.fansen.phr.entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/11/3.
 */
public class Prescription implements Serializable{
    private int _id;
    private Bitmap prescriptionCaptureImage;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Bitmap getPrescriptionCaptureImage() {
        return prescriptionCaptureImage;
    }

    public void setPrescriptionCaptureImage(Bitmap prescriptionCaptureImage) {
        this.prescriptionCaptureImage = prescriptionCaptureImage;
    }
}
