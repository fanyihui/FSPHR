package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class Department {
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
