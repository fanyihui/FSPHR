package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Encounter implements Serializable{


    private long encounter_key;
    private String encounter_number;
    private Organization org;
    private Department department;
    private Person person;
    private Date encounter_date;
    private String diagnosis;

    public long getEncounter_key() {
        return encounter_key;
    }

    public void setEncounter_key(long encounter_key) {
        this.encounter_key = encounter_key;
    }

    public String getEncounter_number() {
        return encounter_number;
    }

    public void setEncounter_number(String encounter_number) {
        this.encounter_number = encounter_number;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getEncounter_date() {
        return encounter_date;
    }

    public void setEncounter_date(Date encounter_date) {
        this.encounter_date = encounter_date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
