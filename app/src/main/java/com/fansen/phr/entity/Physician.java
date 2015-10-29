package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/10/27.
 */
public class Physician implements Serializable{
    private String physicianName;
    private String employeeNo;
    private int physicianKey;

    public String getPhysicianName() {
        return physicianName;
    }

    public void setPhysicianName(String physicianName) {
        this.physicianName = physicianName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public int getPhysicianKey() {
        return physicianKey;
    }

    public void setPhysicianKey(int physicianKey) {
        this.physicianKey = physicianKey;
    }
}
