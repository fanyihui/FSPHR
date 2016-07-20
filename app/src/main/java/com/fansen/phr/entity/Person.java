package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Yihui Fan on 2015/9/22.
 */


public class Person implements Serializable{
    private long id;
    private String person_name;
    private String gender_code;
    private Date birthday;
    private List<PersonID> personIDs;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
