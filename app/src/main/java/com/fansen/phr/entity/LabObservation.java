package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/11/24.
 */
public class LabObservation implements Serializable{
    private int _id;
    private ObservationDef observationDef;
    private String value;
    private String unit;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
