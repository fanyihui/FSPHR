package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class Diagnosis implements Serializable{
    private long diagnosis_key;
    private long encounter_key;
    private int primaryIndicator;
    private String status;
    private DictDiagnosis diagnosis_dict;

    public long getDiagnosis_key() {
        return diagnosis_key;
    }

    public void setDiagnosis_key(long diagnosis_key) {
        this.diagnosis_key = diagnosis_key;
    }

    public long getEncounter_key() {
        return encounter_key;
    }

    public void setEncounter_key(long encounter_key) {
        this.encounter_key = encounter_key;
    }

    public DictDiagnosis getDiagnosis_dict() {
        return diagnosis_dict;
    }

    public void setDiagnosis_dict(DictDiagnosis diagnosis_dict) {
        this.diagnosis_dict = diagnosis_dict;
    }

    public int getPrimaryIndicator() {
        return primaryIndicator;
    }

    public void setPrimaryIndicator(int primaryIndicator) {
        this.primaryIndicator = primaryIndicator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
