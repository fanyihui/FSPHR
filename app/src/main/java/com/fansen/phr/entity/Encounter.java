package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Yihui on 2015/9/22.
 */
public class Encounter implements Serializable{


    private long encounter_key;
    private String encounter_number;
    private Organization org;
    private Department department;
    private Person person;
    private Date admit_date;
    private Date discharge_date;
    private List<Diagnosis> diagnosisList;
    private DictDiagnosis primaryDiagnosis;
    private Physician attendingDoctor;
    private String problem_description;
    private String historicalProblems;
    private String physicalExam;

    public Date getDischarge_date() {
        return discharge_date;
    }

    public void setDischarge_date(Date discharge_date) {
        this.discharge_date = discharge_date;
    }

    public long getEncounter_key() {
        return encounter_key;
    }

    public void setEncounter_key(long encounter_key) {
        this.encounter_key = encounter_key;
    }

    public String getEncounter_number() {
        return encounter_number;
    }

    public void setEncounter_number(String encounter_number) {
        this.encounter_number = encounter_number;
    }

    public Organization getOrg() {
        return org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getAdmit_date() {
        return admit_date;
    }

    public void setAdmit_date(Date admit_date) {
        this.admit_date = admit_date;
    }

    public List<Diagnosis> getDiagnosis() {
        return diagnosisList;
    }

    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosisList = diagnosis;
    }

    public DictDiagnosis getPrimaryDiagnosis() {
        return primaryDiagnosis;
    }

    public void setPrimaryDiagnosis(DictDiagnosis primaryDiagnosis) {
        this.primaryDiagnosis = primaryDiagnosis;
    }

    public Physician getAttendingDoctor() {
        return attendingDoctor;
    }

    public void setAttendingDoctor(Physician attendingDoctor) {
        this.attendingDoctor = attendingDoctor;
    }

    public String getProblem_description() {
        return problem_description;
    }

    public void setProblem_description(String problem_description) {
        this.problem_description = problem_description;
    }

    public List<Diagnosis> getDiagnosisList() {
        return diagnosisList;
    }

    public void setDiagnosisList(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    public String getHistoricalProblems() {
        return historicalProblems;
    }

    public void setHistoricalProblems(String historicalProblems) {
        this.historicalProblems = historicalProblems;
    }

    public String getPhysicalExam() {
        return physicalExam;
    }

    public void setPhysicalExam(String physicalExam) {
        this.physicalExam = physicalExam;
    }
}
