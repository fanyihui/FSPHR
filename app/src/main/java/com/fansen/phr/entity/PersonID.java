package com.fansen.phr.entity;

/**
 * Created by Yihui Fan on 2016/7/12.
 */
public class PersonID {

    private long person_id_key;
    private String personId;
    private Person person;
    private AssignedAuthority assignedAuthority;


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



    public long getPerson_id_key() {
        return person_id_key;
    }

    public void setPerson_id_key(long person_id_key) {
        this.person_id_key = person_id_key;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public AssignedAuthority getAssignedAuthority() {
        return assignedAuthority;
    }

    public void setAssignedAuthority(AssignedAuthority assignedAuthority) {
        this.assignedAuthority = assignedAuthority;
    }
}
