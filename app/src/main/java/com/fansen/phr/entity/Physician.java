package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/10/27.
 */
public class Physician implements Serializable{
    private String physicianName;
    private String employeeNo;
    private long physician_key;
    private Department department;

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

    public long getPhysician_key() {
        return physician_key;
    }

    public void setPhysician_key(long physician_key) {
        this.physician_key = physician_key;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
