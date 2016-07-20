package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class PatientID {

    private long key;
    private String ID;
    private String type;
    private Patient patient;

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
}
