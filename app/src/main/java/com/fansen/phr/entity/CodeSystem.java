package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by Yihui Fan on 2015/11/23.
 */
public class CodeSystem implements Serializable{
    private int _id;
    private String codeSystemOid;
    private String codeSystemName;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCodeSystemOid() {
        return codeSystemOid;
    }

    public void setCodeSystemOid(String codeSystemOid) {
        this.codeSystemOid = codeSystemOid;
    }

    public String getCodeSystemName() {
        return codeSystemName;
    }

    public void setCodeSystemName(String codeSystemName) {
        this.codeSystemName = codeSystemName;
    }
}
