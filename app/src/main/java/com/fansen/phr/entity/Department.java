package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Department implements Serializable{
    private int department_key;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment_key() {
        return department_key;
    }

    public void setDepartment_key(int department_key) {
        this.department_key = department_key;
    }
}
