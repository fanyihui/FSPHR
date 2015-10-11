package com.fansen.phr.entity;

import java.util.List;

/**
 * Created by 310078142 on 2015/9/22.
 */
public class Patient {
    private long pat_key;
    private List<PatientID> pat_ids;
    private Person person;

    public long getPat_key() {
        return pat_key;
    }

    public void setPat_key(long pat_key) {
        this.pat_key = pat_key;
    }

    public List<PatientID> getPat_ids() {
        return pat_ids;
    }

    public void setPat_ids(List<PatientID> pat_ids) {
        this.pat_ids = pat_ids;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
