package com.fansen.phr.entity;

import java.io.Serializable;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class DictDiagnosis implements Serializable {
    private String code;
    private String name;
    private String description;
    private long key;


    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
