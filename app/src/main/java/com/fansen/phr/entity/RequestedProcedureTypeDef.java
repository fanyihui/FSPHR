package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/11/16.
 */
public class RequestedProcedureTypeDef implements Serializable{
    private int _id;
    private String name;
    private String code;
    private String codeSystemName;
    private String codeSystemOID;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }

    public String getCodeSystemOID() {
        return codeSystemOID;
    }

    public void setCodeSystemOID(String codeSystemOID) {
        this.codeSystemOID = codeSystemOID;
    }
}
