package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Organization implements Serializable{
    private int org_key;
    private String org_name;

    public Organization(String org_name){
        this.org_name = org_name;
    }

    public int getOrg_key() {
        return org_key;
    }

    public void setOrg_key(int org_key) {
        this.org_key = org_key;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
