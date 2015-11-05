package com.fansen.phr.entity;

import java.net.URI;

/**
 * Created by Yihui Fan on 2015/9/22.
 */
public class ClinicalDocument {
    private int _id;
    private URI captureImageUri;
    private String documentType;
    private String creatingDate;
    private Physician legalAuthentication;
    private String authenticationDateTime;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public URI getCaptureImageUri() {
        return captureImageUri;
    }

    public void setCaptureImageUri(URI captureImageUri) {
        this.captureImageUri = captureImageUri;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public Physician getLegalAuthentication() {
        return legalAuthentication;
    }

    public void setLegalAuthentication(Physician legalAuthentication) {
        this.legalAuthentication = legalAuthentication;
    }

    public String getAuthenticationDateTime() {
        return authenticationDateTime;
    }

    public void setAuthenticationDateTime(String authenticationDateTime) {
        this.authenticationDateTime = authenticationDateTime;
    }
}
