package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui Fan on 2015/11/3.
 */
public class MedicationDict implements Serializable{
    int _id;
    private String name;
    private String code;
    private String spec;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
