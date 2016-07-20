package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Organization implements Serializable{

    private long org_key = 0;
    private String org_name;

    public Organization(){}

    public Organization(String org_name){
        this.org_name = org_name;
    }

    public long getOrg_key() {
        return org_key;
    }

    public void setOrg_key(long org_key) {
        this.org_key = org_key;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }
}
