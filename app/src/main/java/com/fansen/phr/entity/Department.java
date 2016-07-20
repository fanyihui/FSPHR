package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Department implements Serializable{

    private long department_key;
    private String name;
    private Organization organization;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDepartment_key() {
        return department_key;
    }

    public void setDepartment_key(long department_key) {
        this.department_key = department_key;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
