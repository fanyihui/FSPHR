package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/15.
 */
public class DiagnosticImagingReport implements Serializable {
    private int _id;
    private Date requestedProcedureDate;
    private RequestedProcedureTypeDef requestedProcedureTypeDef;
    private BodyPartDef bodypart;
    private String modality;
    private String result;
    private String findings;
    private String recommendation;
    private List<DiagnosticImage> diagnosticImages;
    private List<ImagingObservation> imagingObservations;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public List<DiagnosticImage> getDiagnosticImages() {
        return diagnosticImages;
    }

    public void setDiagnosticImages(List<DiagnosticImage> diagnosticImages) {
        this.diagnosticImages = diagnosticImages;
    }

    public Date getRequestedProcedureDate() {
        return requestedProcedureDate;
    }

    public void setRequestedProcedureDate(Date requestedProcedureDate) {
        this.requestedProcedureDate = requestedProcedureDate;
    }

    public RequestedProcedureTypeDef getRequestedProcedureTypeDef() {
        return requestedProcedureTypeDef;
    }

    public void setRequestedProcedureTypeDef(RequestedProcedureTypeDef requestedProcedureTypeDef) {
        this.requestedProcedureTypeDef = requestedProcedureTypeDef;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<ImagingObservation> getImagingObservations() {
        return imagingObservations;
    }

    public void setImagingObservations(List<ImagingObservation> imagingObservations) {
        this.imagingObservations = imagingObservations;
    }

    public BodyPartDef getBodypart() {
        return bodypart;
    }

    public void setBodypart(BodyPartDef bodypart) {
        this.bodypart = bodypart;
    }
}
