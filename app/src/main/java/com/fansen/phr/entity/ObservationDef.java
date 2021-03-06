package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/11/16.
 */
public class ObservationDef implements Serializable{
    private int _id;
    private String code;
    private String name;
    private String normalRange;
    private CodeSystem codeSystem;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public CodeSystem getCodeSystem() {
        return codeSystem;
    }

    public void setCodeSystem(CodeSystem codeSystem) {
        this.codeSystem = codeSystem;
    }
}
