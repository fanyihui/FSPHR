package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/24.
 */
public class LabReport implements Serializable{
    private OrderCodeDef orderCode;
    private Date specimenCollectedDate;
    private Date reportDate;
    private SpecimenTypeCodeDef specimenTypeCode;
    private List<LabObservation> observations;
    private List<DiagnosticImage> referenceImages;

    public OrderCodeDef getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(OrderCodeDef orderCode) {
        this.orderCode = orderCode;
    }

    public Date getSpecimenCollectedDate() {
        return specimenCollectedDate;
    }

    public void setSpecimenCollectedDate(Date specimenCollectedDate) {
        this.specimenCollectedDate = specimenCollectedDate;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public SpecimenTypeCodeDef getSpecimenTypeCode() {
        return specimenTypeCode;
    }

    public void setSpecimenTypeCode(SpecimenTypeCodeDef specimenTypeCode) {
        this.specimenTypeCode = specimenTypeCode;
    }

    public List<LabObservation> getObservations() {
        return observations;
    }

    public void setObservations(List<LabObservation> observations) {
        this.observations = observations;
    }

    public List<DiagnosticImage> getReferenceImages() {
        return referenceImages;
    }

    public void setReferenceImages(List<DiagnosticImage> referenceImages) {
        this.referenceImages = referenceImages;
    }
}
