package com.fansen.phr.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yihui Fan on 2015/11/3.
 */
public class MedicationOrder implements Serializable{
    private int _id;
    private MedicationDict medication;
    private float quantity;
    private String quantity_unit;
    private float dosage;
    private String dosage_unit;
    private int frequency_times;
    private int frequency_interval;
    private String frequency_interval_unit;
    private String route;
    private int PRNIndicator;
    private String start_time;
    private String status;
    private String notes;
    private ArrayList<MedicationReminderTimes> MedicationReminderTimes;

    public MedicationDict getMedication() {
        return medication;
    }

    public void setMedication(MedicationDict medication) {
        this.medication = medication;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getQuantity_unit() {
        return quantity_unit;
    }

    public void setQuantity_unit(String quantity_unit) {
        this.quantity_unit = quantity_unit;
    }

    public float getDosage() {
        return dosage;
    }

    public void setDosage(float dosage) {
        this.dosage = dosage;
    }

    public String getDosage_unit() {
        return dosage_unit;
    }

    public void setDosage_unit(String dosage_unit) {
        this.dosage_unit = dosage_unit;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getPRNIndicator() {
        return PRNIndicator;
    }

    public void setPRNIndicator(int PRNIndicator) {
        this.PRNIndicator = PRNIndicator;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getFrequency_times() {
        return frequency_times;
    }

    public void setFrequency_times(int frequency_times) {
        this.frequency_times = frequency_times;
    }

    public int getFrequency_interval() {
        return frequency_interval;
    }

    public void setFrequency_interval(int frequency_interval) {
        this.frequency_interval = frequency_interval;
    }

    public String getFrequency_interval_unit() {
        return frequency_interval_unit;
    }

    public void setFrequency_interval_unit(String frequency_interval_unit) {
        this.frequency_interval_unit = frequency_interval_unit;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<MedicationReminderTimes> getMedicationReminderTimes() {
        return MedicationReminderTimes;
    }

    public void setMedicationReminderTimes(ArrayList<MedicationReminderTimes> medicationReminderTimes) {
        this.MedicationReminderTimes = medicationReminderTimes;
    }
}
