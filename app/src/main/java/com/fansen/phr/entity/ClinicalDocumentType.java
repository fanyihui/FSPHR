package com.fansen.phr.entity;

/**
 * Created by 310078142 on 2015/11/10.
 */
public enum ClinicalDocumentType {
    PRESCRIPTION("PRESCRIPTION"), US_REPORT("US_REPORT"), LAB_REPORT("LAB_REPORT"), RAD_REPORT("RAD_REPORT"), DI_IMAGE("DIAGNOSTIC_IMAGE");

    private String name;

    private ClinicalDocumentType(String documentType){
        this.name = documentType;
    }

    public String getName(){
        return name;
    }
}
