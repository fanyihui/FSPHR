package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class PatientID {
    private String ID;
    private String type;
    private long patient_key;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPatient_key() {
        return patient_key;
    }

    public void setPatient_key(long patient_key) {
        this.patient_key = patient_key;
    }
}
