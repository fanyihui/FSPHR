package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by faen on 2016/3/9.
 */
public class MedicationAdminRecord implements Serializable{
    private int _id;
    private String status;
    private Date adminDate;
    private TimeSlot timeSlot;
    private MedicationOrder medicationOrder;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAdminDate() {
        return adminDate;
    }

    public void setAdminDate(Date adminDate) {
        this.adminDate = adminDate;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public MedicationOrder getMedicationOrder() {
        return medicationOrder;
    }

    public void setMedicationOrder(MedicationOrder medicationOrder) {
        this.medicationOrder = medicationOrder;
    }
}
