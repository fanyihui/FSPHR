package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 310078142 on 2015/11/24.
 */
public class LabReport implements Serializable{
    private int _id;
    private OrderCodeDef orderCode;
    private Date specimenCollectedDate;
    private Date reportDate;
    private SpecimenTypeCodeDef specimenTypeCode;
    private ArrayList<LabObservation> observations;
    private ArrayList<DiagnosticImage> referenceImages;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public ArrayList<LabObservation> getObservations() {
        return observations;
    }

    public void setObservations(ArrayList<LabObservation> observations) {
        this.observations = observations;
    }

    public ArrayList<DiagnosticImage> getReferenceImages() {
        return referenceImages;
    }

    public void setReferenceImages(ArrayList<DiagnosticImage> referenceImages) {
        this.referenceImages = referenceImages;
    }
}
