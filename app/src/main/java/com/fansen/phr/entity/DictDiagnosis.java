package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/10/20.
 */
public class DictDiagnosis {
    private String code;
    private String name;
    private String description;
    private int key;


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
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
