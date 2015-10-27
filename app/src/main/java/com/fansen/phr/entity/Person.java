package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class Person implements Serializable{
    private long person_key;
    private String person_name;
    private String gender_code;
    private Date birthday;

    public long getPerson_key() {
        return person_key;
    }

    public void setPerson_key(long person_key) {
        this.person_key = person_key;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getGender_code() {
        return gender_code;
    }

    public void setGender_code(String gender_code) {
        this.gender_code = gender_code;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
