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
    private MedicationReminderTimes MedicationReminderTimes;
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

    public MedicationReminderTimes getMedicationReminderTimes() {
        return MedicationReminderTimes;
    }

    public void setMedicationReminderTimes(MedicationReminderTimes medicationReminderTimes) {
        this.MedicationReminderTimes = medicationReminderTimes;
    }

    public MedicationOrder getMedicationOrder() {
        return medicationOrder;
    }

    public void setMedicationOrder(MedicationOrder medicationOrder) {
        this.medicationOrder = medicationOrder;
    }
}
