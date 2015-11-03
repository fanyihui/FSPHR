package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/10/27.
 */
public class ChiefComplaint implements Serializable{
    private int key;
    private String symptom;
    private String duration;
    private String duration_unit;

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration_unit() {
        return duration_unit;
    }

    public void setDuration_unit(String duration_unit) {
        this.duration_unit = duration_unit;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
