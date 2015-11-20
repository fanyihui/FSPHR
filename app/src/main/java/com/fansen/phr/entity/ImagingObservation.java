package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 310078142 on 2015/11/16.
 */
public class ImagingObservation implements Serializable{
    private int _id;
    private ObservationDef observationDef;
    private String value;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ObservationDef getObservationDef() {
        return observationDef;
    }

    public void setObservationDef(ObservationDef observationDef) {
        this.observationDef = observationDef;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
